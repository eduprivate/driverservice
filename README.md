#Solução

Para a aplicação foi escolhido o springboot para desenvolver os serviços. Boa documentação, fácil utilização e fácil 
transporte (pacote jar).

O desenvolvimento foi baseado em TDD, ou seja, primeiro os testes depois a implementação. 

Para trazer os taxistas estou pesquisando dos os taxistas cujas coordenadas estão dentro de uma retângulo geográfico. Depois 
cálculo o baricentro do retângulo geográfico supondo como o ponto do usuário. Calculo a distância de todos os taxistas ao 
baricentro e retorno uma lista ordenada pelas menores distâncias.

Um segundo controller foi desenvolvido utilizando Redis para cachear os status dos taxistas. (Só por brincadeira). A Aplicação
continua funcionando mesmo se o servidor Redis estiver down.

Escrevi um client web em AngularJS que consulta os serviços. O cliente Web esta embedded na aplicação do (para ajudar
a avaliação).

Sem autenticação (por enquanto!)

# Requisitos:
JRE - Java 7
-> Usada: Java(TM) SE Runtime Environment (build 1.7.0_79-b15)

Redis - Versão 3.0.3 ou superior rodando na porta 6379 (default). 
Obs.: A Aplicação funcionará mesmo se o servidor Redis estiver down

# Build:
$ mvn clean package

$ java -jar target/driverservice.jar 

# Entry-points  

GET - http://localhost:8080/drivers/1/status
GET - http://localhost:8080/drivers/inArea?sw=-23.612474,-46.702746&ne=-23.589548,-46.673392

POST - http://localhost:8080/drivers/status
Exemplo de raw body: {"latitude":-23.60810717,"longitude":-46.67500346,"driverId":1,"driverAvailable":true}

POST - http://localhost:8080/drivers/
Exemplo de raw body: {"name":"David Doe", "plate":"ATX-0098"}

# Cached Entry-points  

GET - http://localhost:8080/cached/drivers/1/status
GET - http://localhost:8080/cached/drivers/inArea?sw=-23.612474,-46.702746&ne=-23.589548,-46.673392

POST - http://localhost:8080/cached/drivers/status
Exemplo de raw body: {"latitude":-23.60810717,"longitude":-46.67500346,"driverId":1,"driverAvailable":true}

POST - http://localhost:8080/cached/drivers/
Exemplo de raw body: {"name":"David Doe", "plate":"ATX-0098"}


# AWS

http://ec2-52-35-93-205.us-west-2.compute.amazonaws.com/
