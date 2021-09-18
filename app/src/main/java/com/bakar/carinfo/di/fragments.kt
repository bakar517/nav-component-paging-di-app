package com.bakar.carinfo.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bakar.carinfo.builtdate.CarBuiltDateFragment
import com.bakar.carinfo.cartype.CarTypeFragment
import com.bakar.carinfo.detail.CarDetailFragment
import com.bakar.carinfo.manufacturer.ManufacturerFragment
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class FragmentKey(val value: KClass<out Fragment>)


@Module
abstract class FragmentModule {

    @Binds
    abstract fun factory(factory: CustomFragmentFactory): FragmentFactory

    @Binds
    @IntoMap
    @FragmentKey(ManufacturerFragment::class)
    abstract fun manufacturerFragment(fragment: ManufacturerFragment): Fragment


    @Binds
    @IntoMap
    @FragmentKey(CarTypeFragment::class)
    abstract fun contributeCarModelFragment(fragment: CarTypeFragment): Fragment


    @Binds
    @IntoMap
    @FragmentKey(CarBuiltDateFragment::class)
    abstract fun contributeCarBuiltDateFragment(fragment: CarBuiltDateFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(CarDetailFragment::class)
    abstract fun contributeCarDetailFragment(fragment: CarDetailFragment): Fragment


}

class CustomFragmentFactory @Inject constructor(
    private val creators: Map<Class<out Fragment>, @JvmSuppressWildcards Provider<Fragment>>
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        val modelClass = loadFragmentClass(classLoader, className)
        val provider = creators[modelClass]
            ?: creators.entries.firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
        return provider?.get() ?: super.instantiate(classLoader, className)
    }
}

