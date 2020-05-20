/*
 *  Copyright (c) 2020 Otávio Santana and others
 *   All rights reserved. This program and the accompanying materials
 *   are made available under the terms of the Eclipse Public License v1.0
 *   and Apache License v2.0 which accompanies this distribution.
 *   The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 *   and the Apache License v2.0 is available at http://www.opensource.org/licenses/apache2.0.php.
 *
 *   You may elect to redistribute this code under either of these licenses.
 *
 *   Contributors:
 *
 *   Otavio Santana
 */

package org.eclipse.jnosql.diana.cassandra.column;

import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.delete.Delete;
import com.datastax.oss.driver.api.querybuilder.delete.DeleteSelection;
import jakarta.nosql.column.ColumnDeleteQuery;

final class DeleteQueryConverter {
    private DeleteQueryConverter() {
    }

    static Delete delete(ColumnDeleteQuery query, String keyspace) {
        final String columnFamily = query.getColumnFamily();
        DeleteSelection deleteSelection = QueryBuilder.deleteFrom(keyspace, columnFamily);

        for (String column : query.getColumns()) {
            deleteSelection = deleteSelection.column(QueryUtils.getName(column));
        }

        final Delete delete = deleteSelection.where(Relations.createClause(query.getCondition().orElse(null)));
        return delete;
    }
}
