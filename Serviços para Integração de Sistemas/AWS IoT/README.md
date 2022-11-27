Para realizar a atividade foi necessário seguir o passo a passo do PDF Introdução à AWS IoT Core

Foram criados duas regras a partir do gerenciamento de roteamento de mensagem com as respectivas ações.
Uma regra para persistir os dados em uma base DynamoDB e outra para disparar um e-mail para um destinatário com uma regra específica através do SNS.

Para a regra com DynamoDB foi utilizada a seguinte instrução SQL: 
SELECT * FROM 'esp8266/pub'

Para a regra de disparo de e-mail foi utilizada a seguinte instrução SQL:
SELECT temperature FROM 'esp8266/pub' WHERE temperature > 30

Ainda sobre o teste de notificação por e-mail foi utilizado o serviço Mailinator.
Todo o passo a passo foi registrado e enumerado com a descrição reverente a cada etapa da atividade.

O arquivo secrets.h não foi versionado.