# Gestión de Agenda

**2024**

**Solución eficiente y robusta para la organización de actividades diarias**

**UNITY GROUP**

Somos un equipo de tres apasionados desarrolladores dedicados a crear soluciones tecnológicas innovadoras que transforman la manera en que las personas gestionan su tiempo.

## Sobre nosotros

**Valores**
- Transparencia.
- Honestidad.
- Coherencia.
- Libertad.
- Puntualidad en las entregas.
- Excelencia.
- Adaptabilidad.
- Diligencia.

**Nuestro equipo**
- Carlos
- Tomás
- Juli Ana

## Índice

- [Presentación](#presentación)
- [Beneficios](#beneficios)
- [Tecnología y herramientas](#tecnología-y-herramientas)
- [Planning del proyecto](#planning-del-proyecto)
- [Resultados esperados](#resultados-esperados)
- [Detalles técnicos. Clases](#detalles-técnicos-clases)
- [Conclusión](#conclusión)

## Presentación <a name="presentación"></a>

Gestión de Agenda es una solución eficiente y robusta para la organización de actividades diarias, diseñada para mejorar la productividad y la organización personal.

## Beneficios

- **Ahorro de tiempo**: Mejora significativa en la eficiencia de la gestión de tiempo.
- **Mejor organización de actividades**: Centraliza la planificación y seguimiento de actividades.
- **Reducción del riesgo de olvidar**: Recordatorios automáticos y alertas.
- **Acceso desde cualquier lugar y dispositivo**: Facilita la gestión en movimiento.

## Tecnología y herramientas <a name="tecnología-y-herramientas"></a>

- **Interfaz**: HTML5
- **Lenguaje de programación**: Java SE
- **IDE**: IntelliJ

## Planning del proyecto <a name="planning-del-proyecto"></a>

El proyecto se divide en varias etapas, comenzando con el análisis del problema, seguido del diseño y la implementación del programa, y concluyendo con pruebas y depuración.

## Resultados esperados <a name="resultados-esperados"></a>

Al finalizar el proyecto, se espera contar con una aplicación de gestión de agenda intuitiva que facilite la organización y seguimiento de peticiones de reserva, además de generar un archivo de incidencias.

## Detalles técnicos. Clases <a name="detalles-técnicos-clases"></a>

- **ConfigManager**: Actúa como un contenedor para almacenar y gestionar los valores de configuración.
- **RequestManager**: Procesa solicitudes de reserva desde un archivo de texto, convirtiéndolas en objetos Reserva.
- **HTMLGenerator**: Genera archivos HTML que muestran una agenda de reservas para un espacio específico durante un mes dado.
- **MaskHours / MaskDiasSemana**: Capturan y limpian los datos que llegan desde requests y anteriormente los que salen de los archivos de texto.
- **ReservaManager**: Maneja y valida reservas, identificando conflictos de fechas y horarios en la misma sala.

## Conclusión <a name="conclusión"></a>

La arquitectura del proyecto de agenda está diseñada para ser modular, flexible y eficiente, permitiendo la gestión precisa de reservas y la generación de informes detallados. Al trabajar juntas, las clases encapsulan diversas funcionalidades, desde la entrada y procesamiento de datos hasta la generación de salida mediante una tabla en HTML y un archivo de texto que se encargará de capturar las incidencias en la lista de peticiones, todo ello enfocado en cumplir con los objetivos del proyecto y satisfacer las necesidades del cliente y usuarios finales.
