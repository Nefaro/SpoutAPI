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
package org.spout.api.material;

import org.spout.api.Source;
import org.spout.api.collision.BoundingBox;
import org.spout.api.collision.CollisionVolume;
import org.spout.api.geo.World;
import org.spout.api.material.block.BlockFace;

public abstract class BlockMaterial extends Material {

	public static final BlockMaterial AIR = register(new GenericBlockMaterial("Air", 0).setFriction(0.0F).setHardness(0.0F).setOpacity((byte) 0));

	public BlockMaterial(String name, int typeId) {
		super(name, typeId);
	}

	public BlockMaterial(String name, int typeId, int data, Material parent) {
		super(name, typeId, data, parent);
	}

	/**
	 * Gets the block at the given id, or null if none found
	 * 
	 * @param id to get
	 * @return block, or null if none found
	 */
	public static BlockMaterial get(short id) {
		Material mat = Material.get(id);
		if (mat instanceof BlockMaterial) {
			return (BlockMaterial) mat;
		} else {
			return null;
		}
	}

	/**
	 * Gets the associated block material with it's name. Case-insensitive.
	 * 
	 * @param name to lookup
	 * @return material, or null if none found
	 */
	public static BlockMaterial get(String name) {
		Material mat = Material.get(name);
		if (mat instanceof BlockMaterial) {
			return (BlockMaterial) mat;
		} else {
			return null;
		}
	}

	private final BoundingBox area = new BoundingBox(0F, 0F, 0F, 1F, 1F, 1F);
	private float hardness = 0F;
	private float friction = 0F;
	private byte opacity = 0xF;
	private byte lightLevel = 0;

	/**
	 * Gets the friction of this block
	 * 
	 * @return friction value
	 */
	public float getFriction() {
		return this.friction;
	}

	/**
	 * Sets the friction of this block
	 * 
	 * @param slip friction value
	 * @return this material
	 */
	public BlockMaterial setFriction(float slip) {
		this.friction = slip;
		return this;
	}

	/**
	 * Gets the hardness of this block
	 * 
	 * @return hardness value
	 */
	public float getHardness() {
		return this.hardness;
	}

	/**
	 * Sets the hardness of this block
	 * 
	 * @param hardness hardness value
	 * @return this material
	 */
	public BlockMaterial setHardness(float hardness) {
		this.hardness = hardness;
		return this;
	}

	/**
	 * Gets the amount of light this block emits
	 * 
	 * @return light level
	 */
	public byte getLightLevel() {
		return this.lightLevel;
	}

	/**
	 * Sets the amount of light this block emits
	 * 
	 * @param level
	 * @return this material
	 */
	public BlockMaterial setLightLevel(byte level) {
		this.lightLevel = level;
		return this;
	}

	/**
	 * True if this block is a type of liquid
	 * 
	 * @return liquidity
	 */
	public abstract boolean isLiquid();

	/**
	 * Gets the amount of light blocked by this block.
	 * 
	 * 0xF (15) represents a fully opaque block.
	 * 
	 * @return opacity
	 */
	public byte getOpacity() {
		return this.opacity;
	}

	/**
	 * Sets the amount of light blocked by this block.
	 * 
	 * 0xF (15) represents a fully opaque block.
	 * 
	 * @param level of opacity
	 * @return this material
	 */
	public BlockMaterial setOpacity(byte level) {
		this.opacity = level;
		return this;
	}

	/**
	 * Gets the bounding box area of this material
	 * 
	 * @return area
	 */
	public CollisionVolume getBoundingArea() {
		return this.area;
	}

	/**
	 * True if this block acts as an obstacle when placing a block on it false
	 * if not.
	 * 
	 * If the block is not an obstacle, placement will replace this block.
	 * 
	 * @return if this block acts as a placement obstacle
	 */
	public abstract boolean isPlacementObstacle();

	/**
	 * True if this block requires physic updates when a neighbor block changes,
	 * false if not.
	 * 
	 * @return if this block requires physics updates
	 */
	public abstract boolean hasPhysics();

	/**
	 * Called when a block adjacent to this material is changed.
	 * 
	 * @param world that the material is in
	 * @param x coordinate for this material
	 * @param y coordinate for this material
	 * @param z coordinate for this material
	 */
	public abstract void onUpdate(World world, int x, int y, int z);

	/**
	 * Called when this block has been destroyed.
	 * 
	 * @param world that the material is in
	 * @param x coordinate for this material
	 * @param y coordinate for this material
	 * @param z coordinate for this material
	 */
	public abstract void onDestroy(World world, int x, int y, int z);

	/**
	 * Called when this block is placed.
	 * 
	 * @param world that the material is in
	 * @param x coordinate for this material
	 * @param y coordinate for this material
	 * @param z coordinate for this material
	 * @param data block data to use during placement
	 * @param against face against the block is placed
	 * @param source source of this placement
	 * @return true if placement is handled
	 */
	public boolean onPlacement(World world, int x, int y, int z, short data, BlockFace against, Source source) {
		return world.setBlockMaterial(x, y, z, this, data, true, source);
	}
}
