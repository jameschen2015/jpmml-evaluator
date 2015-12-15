/*
 * Copyright (c) 2015 Villu Ruusmann
 *
 * This file is part of JPMML-Evaluator
 *
 * JPMML-Evaluator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JPMML-Evaluator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with JPMML-Evaluator.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jpmml.rattle;

import org.dmg.pmml.MiningFunctionType;
import org.dmg.pmml.MiningSchema;
import org.dmg.pmml.Node;
import org.dmg.pmml.ScoreDistribution;
import org.dmg.pmml.TreeModel;
import org.dmg.pmml.True;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NodeTransformerTest {

	@Test
	public void transform(){
		Node node = new Node()
			.setPredicate(new True())
			.setScore("1")
			.addScoreDistributions(new ScoreDistribution("0", 0), new ScoreDistribution("1", 100));

		TreeModel treeModel = new TreeModel(MiningFunctionType.CLASSIFICATION, new MiningSchema(), node);

		NodeTransformer transformer = new NodeTransformer();
		transformer.applyTo(treeModel);

		assertTrue(node.hasScoreDistributions());

		treeModel.setFunctionName(MiningFunctionType.REGRESSION);

		transformer.applyTo(treeModel);

		assertFalse(node.hasScoreDistributions());
	}
}