# SysImg - Backend
Construído usando a linguagem Java, servidor web Jetty, servlets e banco de dados MySQL usando JDBC.

## Para rodar o backend:

### 0 - Se tiver firewall configure o mesmo para aceitar as conexões nas respectivas portas:

### 1 - Crie uma database do MySQL:
- Instale o MySQL
    - No Linux(Debian based - Ubuntu/Mint): `apt install mariadb-server`     <--- Nome do MySQL no Linux
    - No Windows: Não sei...
    - No console entre no cliente mysql usando: `mysql`
    - Vai solicitar a senha do usuário root (linux) ou administrador (windows)
#### Dentro deste cliente (mysql) copie e cole as seguintes linhas:
    CREATE DATABASE sysimg;
    CREATE DATABASE sysimg_des;
    CREATE USER 'sysimg'@'localhost' IDENTIFIED BY 'n2Qi4RWl';
    CREATE USER 'sysimg_des'@'localhost' IDENTIFIED BY 'D8yx3Rk2';
    GRANT ALL PRIVILEGES ON sysimg.* TO 'sysimg'@'localhost' WITH GRANT OPTION;
    GRANT ALL PRIVILEGES ON sysimg_des.* TO 'sysimg_des'@'localhost' WITH GRANT OPTION;`



### 2 - Defina o IP e a porta desejada:
Altere o arquivo `pom.xml`.
Na seção de configuração do servidor Jetty:

    <plugin>
        <groupId>org.eclipse.jetty</groupId>
        <artifactId>jetty-maven-plugin</artifactId>
        <version>11.0.20</version>
        <configuration>
            <httpConnector>
                <host>192.168.1.20</host>
                <port>4100</port>
            </httpConnector>
            <webApp>
                <contextPath>/app</contextPath>
            </webApp>
            <scan>10</scan>
        </configuration>
    </plugin>

#### Altere a linha
`<host>192.168.1.20</host>` (sugestão para testes)
#### Para
    <host>127.0.0.1</host>

#### E a linha
`<port>4100</port>`
#### Para
    <port>4000</port> (sugestão para testes)

### 3 - Inicie o servidor Jetty usando o Maven:
    mvn Jetty:run

Se der certo deve aparecer no console:<br>
[INFO] Started ServerConnector@6d7677d8{HTTP/1.1, (http/1.1)}{127.0.0.1:4000}<br>
[INFO] Started Server@2e4b5da1{STARTING}[11.0.20,sto=0] @4139ms<br>
[INFO] Scan interval sec = 10<br>

### 4 - Teste o backend:

#### Opção 1: Instale o backend_de_testes:
    https://github.com/SysImg-Project/teste_de_backend

#### Opção 2: (mais prática) Use o cURL:
- Instale o cURL
- No Linux(Debian based - Ubuntu/Mint): `apt install curl`
- No Windows: Não sei...

- Para:
  - Criar um user (não precisa mandar nenhum dado (mas pode se quiser -- veja abaixo)):
    ##### - Envie para o backend:
        curl --header "Content-Type: application/json" --header "Accept: application/json" --data '{"object":"user","action":"create","data":null,"condiction":null}' http://127.0.0.1:4000/app/endpoint/crud

    - Resposta do backend:
    {"msg":"","data":{"senha":"","tipo":0,"datanas":"0000-01-01","nome":"","id":1,"sobrenome":"","sexo":0,"login":""},"status":"OK"}


  - Criar um user (mandando dados do mesmo):
    ##### - Envie para o backend:
        curl --header "Content-Type: application/json" --header "Accept: application/json" --data '{"object":"user","action":"create","data":{"id":0,"nome":"","sobrenome":"","datanas":"0000-01-01","sexo":"0","tipo":"0","login":"","senha":""},"condiction":null}' http://127.0.0.1:4000/app/endpoint/crud

    - Resposta do backend:
    {"msg":"","data":{"senha":"","tipo":0,"datanas":"0000-01-01","nome":"","id":1,"sobrenome":"","sexo":0,"login":""},"status":"OK"}


  - Ler um user (precisa indicar a 'id' do user):
      ##### - Envie para o backend:
        curl --header "Content-Type: application/json" --header "Accept: application/json" --data '{"object":"user","action":"read","data":{"id":1},"condiction":null}' http://127.0.0.1:4000/app/endpoint/crud

    - Resposta do backend:
    {"msg":"","data":{"senha":"","tipo":0,"datanas":"0000-01-01","nome":"","id":13,"sobrenome":"","sexo":0,"login":""},"status":"OK"}


  - Atualizar um user (precisa indicar a 'id' do user e os campos que deseja atualizar):
      ##### - Envie para o backend:
          curl --header "Content-Type: application/json" --header "Accept: application/json" --data '{"object":"user","action":"update","data":{"id":1,"nome":"Paulo","sobrenome":"Griebler","datanas":"1974-03-28","sexo":"1","tipo":"5","login":"griebler","senha":"12345678"},"condiction":null}' http://127.0.0.1:4000/app/endpoint/crud

    - Resposta do backend:
   {"msg":"","data":{"senha":"12345678","tipo":5,"datanas":"1974-03-28","nome":"Paulo","id":1,"sobrenome":"Griebler","sexo":1,"login":"griebler"},"status":"OK"}


  - Deletar um user (precisa indicar a 'id' do user):
      ##### - Envie para o backend:
           curl --header "Content-Type: application/json" --header "Accept: application/json" --data '{"object":"user","action":"delete","data":{"id":1},"condiction":null}' http://127.0.0.1:4000/app/endpoint/crud

    - Resposta do backend:
   {"msg":"","status":"OK"}


  - Deletar todos os users:
      ##### - Envie para o backend:
           curl --header "Content-Type: application/json" --header "Accept: application/json" --data '{"object":"user","action":"delete_all","data":null},"condiction":null}' http://127.0.0.1:4000/app/endpoint/crud

  - Resposta do backend:
  {"msg":"","status":"OK"}


  - Listar todos os users:
      ##### - Envie para o backend:
           curl --header "Content-Type: application/json" --header "Accept: application/json" --data '{"object":"user","action":"list","data":null,"condiction":null}' http://127.0.0.1:4000/app/endpoint/crud

    - Resposta do backend:
    {"msg":"","data":{"array":[{"senha":"","tipo":0,"datanas":"","nome":"","id":1,"sobrenome":"","sexo":0,"login":""},{"senha":"12345678","tipo":5,"datanas":"1974-03-28","nome":"Paulo","id":2,"sobrenome":"Griebler Júnior","sexo":1,"login":"griebler"}]},"status":"OK"}


  - Listar um user com uma condição específica:
      ##### - Envie para o backend:
           curl --header "Content-Type: application/json" --header "Accept: application/json" --data '{"object":"user","action":"list","data":null,"condiction":"WHERE nome='Paulo'"}' http://127.0.0.1:4000/app/endpoint/crud

    - Resposta do backend:
    {"msg":"","data":{"senha":"12345678","tipo":5,"datanas":"1974-03-28","nome":"Paulo","id":2,"sobrenome":"Griebler Júnior","sexo":1,"login":"griebler"},"status":"OK"}



