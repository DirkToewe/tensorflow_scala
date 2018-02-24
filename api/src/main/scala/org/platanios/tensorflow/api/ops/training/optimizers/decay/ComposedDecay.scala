/* Copyright 2017, Emmanouil Antonios Platanios. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.platanios.tensorflow.api.ops.training.optimizers.decay

import org.platanios.tensorflow.api.ops.Output
import org.platanios.tensorflow.api.ops.variables.Variable

/** Decay method helper for composing two existing learning rate decay methods.
  *
  * The resulting learning rate is the initial learning rate after having applied `decay2` on it, and then `decay1`.
  *
  * @param  decay1 First decay method.
  * @param  decay2 Second decay method.
  *
  * @author Emmanouil Antonios Platanios
  */
class ComposedDecay protected (
    val decay1: Decay,
    val decay2: Decay
) extends Decay {
  /** Applies the decay method to `value`, the current iteration in the optimization loop is `step` and returns the
    * result.
    *
    * @param  value Value to decay.
    * @param  step  Option containing current iteration in the optimization loop, if one has been provided.
    * @return Decayed value.
    * @throws IllegalArgumentException If the decay method requires a value for `step` but the provided option is empty.
    */
  @throws[IllegalArgumentException]
  override def apply(value: Output, step: Option[Variable]): Output = {
    decay1(decay2(value, step), step)
  }
}

object ComposedDecay {
  def apply(decay1: Decay, decay2: Decay): ComposedDecay = {
    new ComposedDecay(decay1, decay2)
  }
}
