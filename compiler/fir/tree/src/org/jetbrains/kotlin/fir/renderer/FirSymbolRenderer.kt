/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.renderer

import org.jetbrains.kotlin.fir.declarations.FirContextReceiver
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol
import org.jetbrains.kotlin.fir.symbols.impl.FirReceiverParameterSymbol

open class FirSymbolRenderer {

    internal lateinit var components: FirRendererComponents
    protected val printer: FirPrinter get() = components.printer

    open fun printReference(symbol: FirBasedSymbol<*>) {
        printer.print(renderReference(symbol))
    }

    protected open fun renderReference(symbol: FirBasedSymbol<*>): String {
        return when (symbol) {
            is FirCallableSymbol<*> -> symbol.callableId.toString()
            is FirClassLikeSymbol<*> -> symbol.classId.toString()
            is FirReceiverParameterSymbol -> {
                val fir = symbol.fir
                if (fir is FirContextReceiver) {
                    "context of ${renderReference(fir.containingDeclarationSymbol)}"
                } else if (fir is FirReceiverParameter) {
                    renderReference(fir.containingDeclarationSymbol)
                } else "?"
            }
            else -> "?"
        }
    }
}
