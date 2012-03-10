/*
 * This file is part of SpoutAPI (http://www.spout.org/).
 *
 * SpoutAPI is licensed under the SpoutDev License Version 1.
 *
 * SpoutAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the SpoutDev License Version 1.
 *
 * SpoutAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the SpoutDev License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://www.spout.org/SpoutDevLicenseV1.txt> for the full license,
 * including the MIT license.
 */
package org.spout.api.basic.generator;

import org.spout.api.basic.blocks.SpoutBlocks;
import org.spout.api.generator.Populator;
import org.spout.api.generator.WorldGenerator;
import org.spout.api.geo.World;
import org.spout.api.geo.discrete.Point;
import org.spout.api.geo.discrete.atomic.Transform;
import org.spout.api.math.Quaternion;
import org.spout.api.math.Vector3;
import org.spout.api.util.cuboid.CuboidShortBuffer;

public class FlatWorldGenerator implements WorldGenerator {

	protected final float spawnX;
	protected final float spawnY;
	protected final float spawnZ;

	public FlatWorldGenerator(float spawnX, float spawnY, float spawnZ) {
		this.spawnX = spawnX;
		this.spawnY = spawnY;
		this.spawnZ = spawnZ;
	}

	public void generate(CuboidShortBuffer blockData, int chunkX, int chunkY, int chunkZ) {
		if (chunkY < 0) {
			blockData.flood(SpoutBlocks.unbreakable.getId());
		}
	}

	public Populator[] getPopulators() {
		return new Populator[0];
	}

	@Override
	public Transform getSpawn(World world) {
		return new Transform(new Point(world, this.spawnX, this.spawnY, this.spawnZ), Quaternion.identity, Vector3.ONE);
	}
}
