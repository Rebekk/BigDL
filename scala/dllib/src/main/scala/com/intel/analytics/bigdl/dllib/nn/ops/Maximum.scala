/*
 * Copyright 2016 The BigDL Authors.
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
package com.intel.analytics.bigdl.dllib.nn.ops

import com.intel.analytics.bigdl.dllib.tensor.Tensor
import com.intel.analytics.bigdl.dllib.tensor.TensorNumericMath.TensorNumeric
import com.intel.analytics.bigdl.dllib.utils.{Log4Error, Table}

import scala.reflect.ClassTag

class Maximum[T: ClassTag, D: ClassTag]
(implicit ev: TensorNumeric[T], ev2: TensorNumeric[D])
  extends Operation[Table, Tensor[D], T] {

  output = Tensor[D]()
  override def updateOutput(input: Table): Tensor[D] = {
    val x = input[Tensor[D]](1)
    val y = input[Tensor[D]](2)

    Log4Error.invalidInputError(x.size().sameElements(y.size()),
      s"the shape of x ${x.size()}, y ${y.size()} should be the same")

    output.resizeAs(x).cmax(x, y)
  }

  override def getClassTagNumerics() : (Array[ClassTag[_]], Array[TensorNumeric[_]]) = {
    (Array[ClassTag[_]](scala.reflect.classTag[T], scala.reflect.classTag[D]),
      Array[TensorNumeric[_]](ev, ev2))
  }
}

object Maximum {
  def apply[T: ClassTag, D: ClassTag]()
  (implicit ev: TensorNumeric[T], ev2: TensorNumeric[D]): Maximum[T, D] =
    new Maximum[T, D]()
}
