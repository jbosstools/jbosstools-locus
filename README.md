# The JBoss Tools Locus project

## Summary

_JBoss Tools Locus_ is an adjunct to [Eclipse Orbit](http://www.eclipse.org/orbit/). This project's purpose is to package non-OSGi POJO jars as OSGi bundles (wrapping them with additional metadata) and to publish them to an update site from which they can be consumed by p2 based builds and products. 

This project's contents should never duplicate what's already in Eclipse Orbit; instead, it should provide newer versions of plugins already in Orbit, or plugins which cannot be included in Orbit for some reason (technical, licensing or otherwise).

## Why Locus ? 

Locus was created because we started seeing a number of jars being repeately added to JBoss Tools or related plugins which Eclipse Orbit for mixed reasons does not contain. Thus we needed something to provide a repository to be used for builds/downloads.

### Why not just use Orbit ? 

Eclipse Orbit's [mandate](http://www.eclipse.org/orbit/) states:

	"The Orbit mandate does not allow the project to be used for
	building or maintaining third-party libraries that are not
	approved by the Eclipse foundation for us in Eclipse
	projects."

This mandate means that if no eclipse.org project requests new jars or updates because of bugs in the
Eclipse.org Orbit or a 3rd party jar are missing there will not be any updates/additions. This have lead to us being
stuck with older or buggy 3rd party jars than what we would like thus we needed to find another way to handle this.

Thus _JBoss Tools Locus_ exists mainly to support development of [JBoss Tools](http://jboss.org/tools), but
the Locus site is not tied to any specific release of JBoss Tools. 

The site it self is not meant to be used directly by users, but mainly used to have a common set of dependencies that
can be used in builds and actual releases.

### Rules/Guidelines

The following is the current guidelines for libraries included in Locus.
They are heavily based by [Orbit](http://wiki.eclipse.org/Adding_Bundles_to_Orbit)'s rules, but adjusted to be more lightweight and with smaller chance of overlap.

1. Do not put anything into Locus before having tried hard to get it into Eclipse Orbit.
    * See [Orbit builds](http://download.eclipse.org/tools/orbit/downloads/) to see existing Orbit content. 
    * See [Orbit FAQ](http://wiki.eclipse.org/index.php/Orbit_Faq) on how to get something into Orbit.
1. Do not include jars directly into the repository, use Maven coordinates for the build as much as possible.
    * Want to keep the repository lean and clean for easy building and contributions.
1. Do not build from source, use the already available public binaries.
    * We are not trying to create forks of libraries.
1. Each plugin should have:
    * LICENSE file with info about the relevant license
    * A matching source bundle
1. Set the Bundle-RequiredExecutionEnvironment header to the absolute minimum JRE required by the library
1. Do always use `org.jboss.tools.locus.<libraryname>` as bundleid
    * The bundle id is set to Locus to avoid any [potential conflicts](http://wiki.eclipse.org/Bundle_Naming) with Orbit.
1. `Bundle-Version` should be the original library version number followed by .qualifier in the fourth segment. In the event that the original number is already four segments, that version number should be used and then followed by "_qualifier"
1. Do not modify the functionallity or behavior of any library.
    * Mockito is the only exception to this because of [this Mockito bug](https://groups.google.com/forum/?hl=en&fromgroups=#!topic/mockito/eLE186uE0uc), documented [here](https://issues.jboss.org/browse/JBIDE-14315)

### How do I use a library from Locus

Where possible use 'Import-Package' instead of 'Require-Bundle' to reduce sensitivity to different bundlings of the same library.

TBA: Info about usage of Locus from .target and pom.xml files

### What does 'Locus' mean ?

Locus has many [meanings](http://www.thefreedictionary.com/locus), one of them is "a set of points whose location satisfies or is determined by one or more specified conditions, the locus of points equidistant from a given point is a circle". Thus it is not an Orbit, but similar.

