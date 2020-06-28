package com.example.proyectocv.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.proyectocv.*
import com.example.proyectocv.Entities.Datos

private val TAB_TITLES = arrayOf(
        R.string.str_datos,
        R.string.str_exp,
        R.string.str_conoc,
        R.string.str_cursos
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager)
    : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        //return PlaceholderFragment.newInstance(position + 1)

        when(position){
            0 -> return DatosFragment()
            1 -> return ExperienciaFragment()
            2 -> return ConocimientosFragment()
            3 -> return CursosFragment()
        }
        return Fragment()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 4 total pages.
        return 4
    }
}