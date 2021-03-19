# Original Bootstrap

Todos os padrões devem ser consultados na [DEV Wiki](https://alm.original.com.br/redmine/projects/dev-wiki/wiki).

## Projetos exemplo

  - [Spring Boot](https://dgit.original.com.br/global/example-rest)
  - [Spring Webflux](https://dgit.original.com.br/global/example-webflux)

# Microserviços com Spring WebFlux
## Setup
### Nexus
Todas as bibliotecas desenvolvidas no Banco Original estão publicadas no **Nexus corporativo** e portanto sua utilização é **obrigatória.**

O repositório do Nexus para microserviços está disponível no endereço:

     https://alm.original.com.br/nexus/content/groups/microservices-maven/

- Usuário: nexus.read
- Senha: original01

**Atenção**: Estas credenciais possuem unicamente as permissões para download das dependências, portanto não servem para acessar a console da ferramenta.

### Maven

Para a configuração do Maven, é necessário realizar o download do arquivo `settings.xml` disponível [aqui](https://alm.original.com.br/redmine/projects/dev-wiki/wiki/_settingsxml_para_build_apotando_para_nexus_do_banco). Dessa maneira, já estará configurado para apontar ao Nexus do banco.

### Gradle
Para configurar o acesso ao **Nexus** adicione:

**gradle.properties**

    mavenUser=nexus.read
    mavenPassword=original01  

**build.gradle**

    //Configurações para busca de dependências externas
    repositories {
        ...
        maven {
            credentials {
                username = "$mavenUser"
                password = "$mavenPassword"
            }
            url = "https://alm.original.com.br/nexus/content/groups/microservices-maven/"
        }
        ...
    }

    ...

    //Configurações de nome, versão e descrição do Projeto
    group = 'br.com.original.*'
    version = '0.0.1-SNAPSHOT'
    description = '*-servicos-java'

    ...

    //Configurações de publicação do Projeto
    publishing {
        publications {
            maven(MavenPublication) {
                from(components.java)
            }
        }
        repositories {
            maven {
                name 'nexus'
                def releasesRepoUrl = "$almNexusDir/repositories/releases"
                def snapshotsRepoUrl = "$almNexusDir/repositories/snapshots"
                url = version.endsWith('SNAPSHOT') ? snapshotsRepoUrl : releasesRepoUrl
                credentials {
                    username repoUser
                    password repoPassword
                }
            }
        }
    }

#### MCSV ####

Caso seu microserviço esteja dentro do domínio *multi-canal*, adicione as seguintes dependências no seu projeto:

    dependencies {
        ...
        implementation 'br.com.original.mcsv:mcsv-common-lib:0.0.19-SNAPSHOT'
        ...
    }


#### Sistemas-produto ####
Caso seu microserviço esteja dentro do domínio de *sistema-produto* por exemplo (trnf, pgto, ctas, etc), adicione as seguintes dependências no seu projeto:

    dependencies {
        ...
        implementation "br.com.original.fwcl:fwcl-integracao-servicos:0.0.9-SNAPSHOT"
        ...
    }


## Intro

> WebFlux é um framework do Spring que nasceu para atender a necessidade de um stack web **não bloqueante** e que utilize o menor número de threads possível para lidar com **concorrência**. Possibilitando que escale utilizando menos recursos de hardware.
<br>
<br>
https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html
<br>
<br>

Há benefícios financeiros em adotar este framework pois reduz a necessidade de escalar a infraestrutura verticalmente. Entretanto, os conceitos de **data streams** e **programação reativa** são essenciais para usufruir destes benefícios. Nativamente, para programação reativa, o framework é suportado pela biblioteca Reactor, utilizando as classes *Mono* e *Flux*

Para mais detalhes sobre Reactor, acesse:
https://projectreactor.io/

Referências adicionais
- https://www.baeldung.com/spring-webflux
- https://gist.github.com/staltz/868e7e9bc2a7b8c1f754
