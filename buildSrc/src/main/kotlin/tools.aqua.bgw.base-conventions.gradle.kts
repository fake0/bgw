/*
 * Copyright 2021-2023 The BoardGameWork Authors
 * SPDX-License-Identifier: Apache-2.0
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import tools.aqua.defaultFormat
import tools.aqua.destabilizesVersion

plugins {
  id("com.dorongold.task-tree")
  id("com.github.ben-manes.versions")
  id("com.diffplug.spotless")
}

group = rootProject.group

version = rootProject.version

repositories { mavenCentral() }

tasks.dependencyUpdates {
  gradleReleaseChannel = "stable"
  rejectVersionIf(destabilizesVersion)
}

spotless { kotlinGradle { defaultFormat(rootProject) } }
