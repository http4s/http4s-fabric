package org.http4s

import _root_.fabric.filter._

/**
 * Default implementation of FabricEntitySupport without any filters
 */
package object fabric extends FabricEntitySupport(NoOpFilter, NoOpFilter)