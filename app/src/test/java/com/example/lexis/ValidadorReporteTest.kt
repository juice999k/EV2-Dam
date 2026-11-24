package com.example.lexis

import com.example.lexis.util.ValidadorReporte
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class ValidadorReporteTest {

    @Test
    fun descripcionVaciaEsRechazada() {
        val resultado = ValidadorReporte.validarDescripcion("")

        assertNotNull(resultado)
    }

    @Test
    fun descripcionConSoloEspaciosEsRechazada() {
        val resultado = ValidadorReporte.validarDescripcion("            ")

        assertNotNull(resultado)
    }

    @Test
    fun descripcionDeNueveCaracteresEsRechazada() {
        val resultado = ValidadorReporte.validarDescripcion("123456789")

        assertNotNull(resultado)
    }

    @Test
    fun descripcionDeDiezCaracteresEsAceptada() {
        val resultado = ValidadorReporte.validarDescripcion("1234567890")

        assertNull(resultado)
    }

    @Test
    fun descripcionDeMilCaracteresEsAceptada() {
        val resultado = ValidadorReporte.validarDescripcion("a".repeat(1000))

        assertNull(resultado)
    }

    @Test
    fun descripcionDeMasDeMilCaracteresEsRechazada() {
        val resultado = ValidadorReporte.validarDescripcion("a".repeat(1001))

        assertNotNull(resultado)
    }

    @Test
    fun espaciosEnLosExtremosNoCompletanElMinimo() {
        val resultado = ValidadorReporte.validarDescripcion("  123456789  ")

        assertNotNull(resultado)
    }
}