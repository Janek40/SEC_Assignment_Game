#!/bin/bash
gradle build
java -jar core/build/libs/core.jar *Plugin/build/classes/main/*Plugin.class
