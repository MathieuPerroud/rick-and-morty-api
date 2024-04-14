package org.mathieu.cleanrmapi.ui.core.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalInspectionMode
import org.koin.compose.LocalKoinScope
import org.koin.compose.rememberKoinInject
import org.koin.core.Koin
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

val IsInPreviewMode: Boolean
    @Composable
    get() = LocalInspectionMode.current

val fakeScope = Scope(
    scopeQualifier = named("fakeScope"),
    id = "fakeScopeId",
    isRoot = false,
    _koin = Koin()
)

@Composable
inline fun <reified T> koinInject(
    valueForPreview: T,
    qualifier: Qualifier? = null,
    scope: Scope = if (IsInPreviewMode) fakeScope else LocalKoinScope.current,
    noinline parameters: ParametersDefinition? = null,
): T {

    if (IsInPreviewMode) return valueForPreview

    return rememberKoinInject(qualifier, scope, parameters)
}