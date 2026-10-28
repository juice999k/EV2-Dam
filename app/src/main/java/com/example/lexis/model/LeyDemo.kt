package com.example.lexis.model

data class Articulo(
    val numero: Int,
    val titulo: String,
    val texto: String
)

object LeyDemo {
    const val TITULO = "Ley de Protección de Datos Personales"
    const val SUBTITULO = "Texto de demostración"

    val articulos: List<Articulo> = listOf(
        Articulo(
            1, "Objeto de la ley",
            "La presente ley tiene por objeto regular el tratamiento de los datos personales de las personas naturales y proteger sus derechos fundamentales frente a su uso indebido."
        ),
        Articulo(
            2, "Definiciones",
            "Para los efectos de esta ley se entenderá por dato personal toda información que permita identificar a una persona, y por tratamiento cualquier operación realizada sobre esos datos, como recolectarlos, almacenarlos, usarlos o comunicarlos."
        ),
        Articulo(
            3, "Principio de licitud",
            "Los datos personales solo podrán tratarse cuando la persona titular haya dado su consentimiento o cuando la ley lo autorice expresamente. El consentimiento debe ser libre, informado y específico."
        ),
        Articulo(
            4, "Principio de finalidad",
            "Los datos deben recogerse con fines determinados y legítimos. No podrán utilizarse posteriormente para propósitos incompatibles con aquellos que motivaron su recolección."
        ),
        Articulo(
            5, "Derechos del titular",
            "Toda persona tiene derecho a acceder a sus datos, rectificarlos cuando sean inexactos, cancelarlos cuando ya no sean necesarios y oponerse a su tratamiento por motivos fundados."
        ),
        Articulo(
            6, "Deber de seguridad",
            "Quien trate datos personales debe adoptar medidas técnicas y organizativas que eviten su pérdida, alteración o acceso no autorizado, y guardar reserva sobre ellos."
        ),
        Articulo(
            7, "Infracciones",
            "El incumplimiento de las obligaciones de esta ley será sancionado con multas proporcionales a la gravedad de la falta, sin perjuicio de la indemnización que corresponda a la persona afectada."
        )
    )
}
