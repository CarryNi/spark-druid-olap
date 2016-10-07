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

import org.scalatest.fixture
import org.sparklinedata.druid.{DefaultSource, NoneGranularity}
import org.sparklinedata.druid.client.CuratorConnection
import org.sparklinedata.druid.metadata.{DruidMetadataCache, DruidRelationOptions, NonAggregateQueryHandling}
import org.sparklinedata.druid.testenv.DruidTestCluster

class CuratorConnectionTest extends fixture.FunSuite with
  fixture.TestDataFixture {

  test("test1") { td =>

    val zkHosts : String = s"${DruidTestCluster.zkConnectString}"
    val options : DruidRelationOptions = DruidRelationOptions(
      1000L,
      1000L,
      false,
      true,
      false,
      30000,
      true,
      "/druid",
      false,
      true,
      Int.MaxValue,
      true,
      NonAggregateQueryHandling.PUSH_NONE,
      NoneGranularity,
      DefaultSource.DEFAULT_ALLOW_TOPN,
      DefaultSource.DEFAULT_TOPN_MAX_THRESHOLD,
      None
    )

    val cc = new CuratorConnection(zkHosts,
      options,
      DruidMetadataCache,
      DruidMetadataCache.thrdPool)

    println(cc.getBroker)
    println(cc.getCoordinator)
  }

}
