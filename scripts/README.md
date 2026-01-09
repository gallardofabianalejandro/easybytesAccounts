# Scripts de Construcción

Esta carpeta contiene scripts útiles para construir las imágenes Docker de los microservicios usando Google Jib.

## Scripts Disponibles

- `build-accounts.bat`: Construye la imagen Docker del módulo accounts.
- `build-cards.bat`: Construye la imagen Docker del módulo cards.
- `build-loans.bat`: Construye la imagen Docker del módulo loans.
- `build-all.bat`: Construye las imágenes Docker de todos los módulos secuencialmente.

## Uso

Para ejecutar un script, simplemente haz doble clic en el archivo `.bat` correspondiente desde el Explorador de Archivos, o ejecútalo desde la línea de comandos:

```
scripts\build-accounts.bat
```

Los scripts cambiarán automáticamente al directorio del módulo correspondiente, ejecutarán `mvn compile jib:build`, y volverán al directorio raíz.

## Requisitos

- Maven instalado y configurado en el PATH.
- Docker corriendo (para que Jib pueda subir la imagen).

## Comandos Equivalentes

Si prefieres ejecutar los comandos manualmente:

- Para accounts: `cd accounts && mvn compile jib:build`
- Para cards: `cd cards && mvn compile jib:build`
- Para loans: `cd loans && mvn compile jib:build`
