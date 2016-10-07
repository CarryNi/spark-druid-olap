/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sparklinedata.druid.client.test

import org.apache.spark.Logging
import org.scalatest.BeforeAndAfterAll

import scala.language.postfixOps

// scalastyle:off line.size.limit
class FilterCTest extends BaseTest with BeforeAndAfterAll with Logging{

  cTest("filterT1",
    "select c_name, sum(c_acctbal) as bal " +
      "from orderLineItemPartSupplier " +
      "where c_mktsegment in ('MACHINERY', 'HOUSEHOLD') and l_shipdate  >= '1994-01-01'  and l_shipdate <= '1994-01-02'" +
      "group by c_name",
    "select c_name, sum(c_acctbal) as bal " +
      "from orderLineItemPartSupplierBase " +
      "where c_mktsegment in ('MACHINERY', 'HOUSEHOLD') and l_shipdate  >= '1994-01-01'  and l_shipdate <= '1994-01-02'" +
      "group by c_name"
  )

  cTest("filterT2",
    "select c_name, sum(c_acctbal) as bal " +
      "from orderLineItemPartSupplier " +
      "where l_linenumber in (1, 2, 3, 4, 5, 6, 7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22," +
      "23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45) and l_shipdate  >= '1994-01-01'  and l_shipdate <= '1994-01-02' " +
      "group by c_name",
    "select c_name, sum(c_acctbal) as bal " +
      "from orderLineItemPartSupplierBase " +
      "where l_linenumber in (1, 2, 3, 4, 5, 6, 7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22," +
      "23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45) and l_shipdate  >= '1994-01-01'  and l_shipdate <= '1994-01-02' " +
      "group by c_name"
  )

  cTest("filterT3",
    "select c_name, sum(c_acctbal) as bal " +
      "from orderLineItemPartSupplier " +
      "where c_mktsegment not in ('MACHINERY', 'HOUSEHOLD') and l_shipdate  >= '1994-01-01'  and l_shipdate <= '1994-01-02' " +
      "group by c_name",
    "select c_name, sum(c_acctbal) as bal " +
      "from orderLineItemPartSupplierBase " +
      "where c_mktsegment not in ('MACHINERY', 'HOUSEHOLD') and l_shipdate  >= '1994-01-01'  and l_shipdate <= '1994-01-02' " +
      "group by c_name"
  )

  cTest("filterT4",
    "select c_name, sum(c_acctbal) as bal " +
      "from orderLineItemPartSupplier " +
      "where c_mktsegment !=  'MACHINERY' and l_shipdate  >= '1994-01-01'  and l_shipdate <= '1994-01-02' " +
      "group by c_name",
    "select c_name, sum(c_acctbal) as bal " +
      "from orderLineItemPartSupplierBase " +
      "where c_mktsegment !=  'MACHINERY' and l_shipdate  >= '1994-01-01'  and l_shipdate <= '1994-01-02' " +
      "group by c_name"
  )

  cTest("filterT5",
    "select s_region, cast(l_shipdate as timestamp) from orderLineItemPartSupplier" +
      " where l_shipdate is not null and l_discount is not null and p_name is not null and l_shipdate  >= '1994-01-01'  and l_shipdate <= '1994-01-02'" +
      " group by s_region, cast(l_shipdate as timestamp) " +
      " order by s_region",
    "select s_region, cast(l_shipdate as timestamp) from orderLineItemPartSupplierBase" +
      " where  l_shipdate is not null and l_discount is not null and p_name is not null and l_shipdate  >= '1994-01-01'  and l_shipdate <= '1994-01-02'" +
      " group by s_region, cast(l_shipdate as timestamp) " +
      " order by s_region"
  )

  /*
   * TODO: in spark cast(l_shipdate as double) is not null is false, even when l_shipdate is not null, why?
   */
  cTest("filterT6",
    "select s_region, cast(l_shipdate as timestamp) from orderLineItemPartSupplier" +
      " where (cast(l_shipdate as double) + 10) is not null and " +
      " (l_discount % 10) is not null and p_name  is not null  and l_shipdate  >= '1994-01-01'  and l_shipdate <= '1994-01-02'" +
      " group by s_region, cast(l_shipdate as timestamp) " +
      " order by s_region",
    "select s_region, cast(l_shipdate as timestamp) from orderLineItemPartSupplierBase" +
      " where (l_shipdate) is not null and " +
      " (l_discount % 10) is not null and p_name  is not null  and l_shipdate  >= '1994-01-01'  and l_shipdate <= '1994-01-02'" +
      " group by s_region, cast(l_shipdate as timestamp) " +
      " order by s_region"
  )

  cTest("filterT7",
    "select s_region, cast(l_shipdate as timestamp) from orderLineItemPartSupplier" +
      " where not(l_shipdate is null) and not(l_discount is null) and not(p_name is null) and l_shipdate  >= '1994-01-01'  and l_shipdate <= '1994-01-02'" +
      " group by s_region, cast(l_shipdate as timestamp) " +
      " order by s_region",
    "select s_region, cast(l_shipdate as timestamp) from orderLineItemPartSupplierBase" +
      " where not(l_shipdate is null) and not(l_discount is null) and not(p_name is null) and l_shipdate  >= '1994-01-01'  and l_shipdate <= '1994-01-02'" +
      " group by s_region, cast(l_shipdate as timestamp) " +
      " order by s_region"
  )

  cTest("filterT8",
    "select s_region, cast(l_shipdate as timestamp) from orderLineItemPartSupplier" +
      " where not((cast(l_shipdate as double) + 10) is null) and  l_shipdate  >= '1994-01-01'  and l_shipdate <= '1994-01-02' and " +
      " not((l_discount % 10) is null) and not(p_name  is null)" +
      " group by s_region, cast(l_shipdate as timestamp) " +
      " order by s_region",
    "select s_region, cast(l_shipdate as timestamp) from orderLineItemPartSupplierBase" +
      " where not(l_shipdate is null) and l_shipdate  >= '1994-01-01'  and l_shipdate <= '1994-01-02' and " +
      " not((l_discount % 10) is null) and not(p_name  is null)" +
      " group by s_region, cast(l_shipdate as timestamp) " +
      " order by s_region"
  )
}
