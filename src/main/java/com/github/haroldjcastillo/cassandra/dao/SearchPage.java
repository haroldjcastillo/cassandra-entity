package com.github.haroldjcastillo.cassandra.dao;

import static com.datastax.driver.core.querybuilder.QueryBuilder.asc;
import static com.datastax.driver.core.querybuilder.QueryBuilder.desc;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.gt;
import static com.datastax.driver.core.querybuilder.QueryBuilder.gte;
import static com.datastax.driver.core.querybuilder.QueryBuilder.lt;
import static com.datastax.driver.core.querybuilder.QueryBuilder.lte;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.querybuilder.Clause;
import com.datastax.driver.core.querybuilder.Ordering;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.querybuilder.Select.Where;
import com.datastax.driver.core.utils.UUIDs;

/**
 * The Class SearchPage.
 *
 * @author harold.castillo
 * @param <T> the generic type
 * @since 03-03-2017 04:28:16 PM
 */
public abstract class SearchPage<T extends EntityBase<T>> {

	protected List<T> timeSearch(final EntityBase<T> entity, final String tableName, final String idProperty, final List<Clause> clauses,
			final List<Ordering> orderings, final TimePage pageLink) {

		final Select select = select().from(tableName);
		final Where query = select.where();

		for (Clause clause : clauses) {
			query.and(clause);
		}

		query.limit(pageLink.getLimit());

		if (pageLink.isAsc()) {
			if (pageLink.getIdOffset() != null) {
				query.and(gt(idProperty, pageLink.getIdOffset()));
			} else if (pageLink.getStartTime() != null) {
				final UUID startOf = UUIDs.startOf(pageLink.getStartTime());
				query.and(gte(idProperty, startOf));
			}
			if (pageLink.getEndTime() != null) {
				final UUID endOf = UUIDs.endOf(pageLink.getEndTime());
				query.and(lte(idProperty, endOf));
			}
		} else {
			if (pageLink.getIdOffset() != null) {
				query.and(lt(idProperty, pageLink.getIdOffset()));
			} else if (pageLink.getEndTime() != null) {
				final UUID endOf = UUIDs.endOf(pageLink.getEndTime());
				query.and(lte(idProperty, endOf));
			}
			if (pageLink.getStartTime() != null) {
				final UUID startOf = UUIDs.startOf(pageLink.getStartTime());
				query.and(gte(idProperty, startOf));
			}
		}
		
		final List<Ordering> orderingList = new ArrayList<>(orderings);
		if (pageLink.isAsc()) {
			orderingList.add(asc(idProperty));
		} else {
			orderingList.add(desc(idProperty));
		}

		query.orderBy(orderingList.toArray(new Ordering[orderingList.size()]));

		return entity.execute(select, ConsistencyLevel.ONE);
	}

	protected List<T> textSearch(final EntityBase<T> entity, final String tableName, final String idProperty, final String searchProperty, final List<Clause> clauses,
			final TextPage textPage) {
		
		List<T> result = Collections.emptyList();
		Select select = select().from(tableName);
		Where query = select.where();
		
		for (Clause clause : clauses) {
			query.and(clause);
		}
		
		query.limit(textPage.getLimit());
		if (!textPage.getOffsetText().isEmpty()) {
			query.and(eq(idProperty, textPage.getOffsetText()));
			query.and(lt(idProperty, textPage.getIdOffset()));
			result = entity.execute(select, ConsistencyLevel.ONE);

			if (result.size() < textPage.getLimit()) {
				select = select().from(tableName);
				query = select.where();
				for (Clause clause : clauses) {
					query.and(clause);
				}
				query.and(gt(searchProperty, textPage.getOffsetText()));
				if (!textPage.getSearchText().isEmpty()) {
					query.and(lt(searchProperty, textPage.getSearchBoundText()));
				}
				int limit = textPage.getLimit() - result.size();
				query.limit(limit);
				
				result.addAll(entity.execute(select, ConsistencyLevel.ONE));
			}

		} else if (!textPage.getSearchText().isEmpty()) {
			query.and(gte(searchProperty, textPage.getSearchText()));
			query.and(lt(searchProperty, textPage.getSearchBoundText()));
			result = entity.execute(select, ConsistencyLevel.ONE);
		} else {
			result = entity.execute(select, ConsistencyLevel.ONE);
		}
		
		return result;
	}

}
