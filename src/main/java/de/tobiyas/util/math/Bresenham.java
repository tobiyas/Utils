/*******************************************************************************
 * Copyright 2014 Tobias Welther
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.tobiyas.util.math;

import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.Location;
import org.bukkit.World;


public class Bresenham {

    /**
     * Prevents any instances from being created
     */
    private Bresenham() {
    }


    /**
     * Generates a 3D Bresenham line between two points.
     *
     * @param a Point to start from. This will be the first element of the list
     * @param b Point to end at. This will be the last element of the list.
     * @return A list of points between a and b.
     */
    public static Queue<Location> line3D(Location a, Location b) {
        return line3D(a.getBlockX(), a.getBlockY(), a.getBlockZ(), b.getBlockX(), b.getBlockY(), b.getBlockZ(), a.getWorld());
    }

    /**
     * Generates a 3D Bresenham line between the given coordinates.
     *
     * @param startx
     * @param starty
     * @param startz
     * @param endx
     * @param endy
     * @param endz
     * @return
     */
    public static Queue<Location> line3D(int startx, int starty, int startz, int endx, int endy, int endz, World world) {
        Queue<Location> result = new LinkedList<Location>();

        int dx = endx - startx;
        int dy = endy - starty;
        int dz = endz - startz;

        int ax = Math.abs(dx) << 1;
        int ay = Math.abs(dy) << 1;
        int az = Math.abs(dz) << 1;

        int signx = (int) Math.signum(dx);
        int signy = (int) Math.signum(dy);
        int signz = (int) Math.signum(dz);

        int x = startx;
        int y = starty;
        int z = startz;

        int deltax, deltay, deltaz;
        if (ax >= Math.max(ay, az)) /* x dominant */ {
            deltay = ay - (ax >> 1);
            deltaz = az - (ax >> 1);
            while (true) {
                result.offer(new Location(world, x, y, z));
                if (x == endx) {
                    return result;
                }

                if (deltay >= 0) {
                    y += signy;
                    deltay -= ax;
                }

                if (deltaz >= 0) {
                    z += signz;
                    deltaz -= ax;
                }

                x += signx;
                deltay += ay;
                deltaz += az;
            }
        } else if (ay >= Math.max(ax, az)) /* y dominant */ {
            deltax = ax - (ay >> 1);
            deltaz = az - (ay >> 1);
            while (true) {
                result.offer(new Location(world, x, y, z));
                if (y == endy) {
                    return result;
                }

                if (deltax >= 0) {
                    x += signx;
                    deltax -= ay;
                }

                if (deltaz >= 0) {
                    z += signz;
                    deltaz -= ay;
                }

                y += signy;
                deltax += ax;
                deltaz += az;
            }
        } else if (az >= Math.max(ax, ay)) /* z dominant */ {
            deltax = ax - (az >> 1);
            deltay = ay - (az >> 1);
            while (true) {
                result.offer(new Location(world, x, y, z));
                if (z == endz) {
                    return result;
                }

                if (deltax >= 0) {
                    x += signx;
                    deltax -= az;
                }

                if (deltay >= 0) {
                    y += signy;
                    deltay -= az;
                }

                z += signz;
                deltax += ax;
                deltay += ay;
            }
        }
        return result;
    }
}
