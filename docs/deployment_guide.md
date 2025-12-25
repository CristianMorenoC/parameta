# Guía de Despliegue en AWS (SAM)

Esta guía detalla paso a paso cómo desplegar los servicios de Vinculación de Empleados y Mock SOAP en tu cuenta de AWS utilizando SAM CLI.

## Prerrequisitos

*   Tener **AWS CLI** y **SAM CLI** instalados.
*   Tener credenciales de AWS configuradas (`~/.aws/credentials` o variables de entorno).

---

## Paso 1: Verificación de Identidad

Antes de desplegar, es crucial verificar que estás usando la cuenta y el usuario correctos.

1.  Abre tu terminal en la carpeta raíz del proyecto.
2.  Ejecuta el siguiente comando:

```bash
aws sts get-caller-identity
```

3.  **Verifica la salida**:
    *   `Account`: Debe coincidir con tu ID de cuenta AWS de destino.
    *   `Arn`: Debe mostrar el usuario o rol con permisos de Administrador que mencionaste tener.

---

## Paso 2: Construcción y Despliegue

### 1. Construir el Proyecto
SAM necesita compilar el código Java y preparar el entorno de ejecucion.

```bash
sam build
```
*Si ves el mensaje `Build Succeeded`, procede al siguiente paso.*

### 2. Despliegue Guiado (Primera vez)
Este comando te hará una serie de preguntas para configurar el despliegue.

```bash
sam deploy --guided
```

### Configuración Recomendada (Responder a las preguntas):
*   **Stack Name**: `demo-vinculacion` (o el nombre que prefieras).
*   **AWS Region**: `us-east-1`
*   **Confirm changes before deploy**: `y` (Te permitirá revisar qué se va a crear).
*   **Allow SAM CLI IAM role creation**: `y` (Necesario para crear permisos de Lambda).
*   **Disable rollback**: `n`
*   **VinculacionApi may not have authorization defined, Is this okay?**: `y` (Para este demo es público).
*   **SoapMockApi may not have authorization defined, Is this okay?**: `y`
*   **Save arguments to configuration file**: `y`
*   **SAM configuration file**: `samconfig.toml`
*   **SAM configuration environment**: `default`

SAM creará un **ChangeSet**. Revisa la lista de recursos (Lambdas, API Gateway, Roles) y confirma con `y` para iniciar el despliegue.

---

## Paso 3: Verificación en Consola AWS

Una vez que SAM termine y veas el mensaje `Successfully created/updated stack`, vamos a verificar la conexión entre servicios.

### 1. Verificar Variables de Entorno
El template está configurado para auto-conectar los servicios, pero es bueno verificarlo.

1.  Entra a la **Consola de AWS** > **Lambda**.
2.  Busca la función que contiene `CoreFunction` en su nombre (ej: `demo-vinculacion-CoreFunction-XXXX`).
3.  Ve a la pestaña **Configuration** > **Environment variables**.
4.  Deberías ver una variable:
    *   **Key**: `SOAP_MOCK_URL`
    *   **Value**: Una URL que termina en `/mock/soap`.
        *   *Esta URL apunta a tu otra Lambda (MockFunction) desplegada.*

### 2. Verificar Logs (CloudWatch)
Para monitorear el funcionamiento:

1.  Ve a **Consola de AWS** > **CloudWatch** > **Log groups**.
2.  Busca el grupo de logs de tu función (ej: `/aws/lambda/demo-vinculacion-CoreFunction...`).
3.  Aquí podrás ver los logs `INFO` y `DEBUG` que configuramos con SLF4J.

---

## Paso 4: Prueba Final (End-to-End)

SAM mostrará las URLs de tu API al final del despliegue en la sección `Outputs`.

Key | Description
--- | ---
`VinculacionApi` | URL del servicio principal
`MockSoapApi` | URL del mock (no necesitas llamarla directo)

### Prueba con cURL

Copia la URL de `VinculacionApi` y ejecuta:

```bash
curl -v "https://<API-ID>.execute-api.us-east-1.amazonaws.com/Prod/api/vinculacion?names=Juan&lastNames=Perez&documentType=CC&documentNumber=123456&birthDate=1990-01-01&vinculationDate=2020-01-01&position=Dev&salary=5000"
```

### Resultado Esperado
Deberías recibir un JSON como este:
```json
{
  "vinculationTime": "X años, Y meses, Z días",
  "currentAge": 33,
  "names": "Juan",
  "lastNames": "Perez"
}
```

Si recibes esto, significa que:
1.  **API Gateway** recibió tu petición.
2.  **Core Lambda** validó y procesó el cálculo.
3.  **Core Lambda** llamó a la **Mock Lambda** vía SOAP exitosamente.
4.  **Mock Lambda** respondió OK.
5.  Todo regresó a ti.
