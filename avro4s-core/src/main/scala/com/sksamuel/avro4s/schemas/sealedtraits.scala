package com.sksamuel.avro4s.schemas

import com.sksamuel.avro4s.SchemaFor
import com.sksamuel.avro4s.typeutils.{Annotations, Names, SubtypeOrdering}
import magnolia.SealedTrait
import org.apache.avro.{Schema, SchemaBuilder}

object SealedTraits {
  def schema[T](ctx: SealedTrait[SchemaFor, T]): Schema = {

    val symbols = ctx.subtypes.sorted(SubtypeOrdering).map { st =>
      Names(st.typeInfo, Annotations(st.annotations)).name
    }

    val names = Names(ctx.typeInfo, Annotations(ctx.annotations))

    val builder = SchemaBuilder.enumeration(names.name).namespace(names.namespace)

    // todo once magnolia supports scala 3 defaults
    //    val builderWithDefault = sealedTraitEnumDefaultValue(ctx) match {
    //      case Some(default) => builder.defaultSymbol(default)
    //      case None          => builder
    //    }
    //

    builder.symbols(symbols: _*)
  }
}