# Componente Leaflet e OpenStreetMap para o Primefaces 3.5


**Autor**: Eder Nilson <[eder.nilson@gmail.com](mailto:eder.nilson@gmail.com)>  
**Copyright**: Eder Nilson  
**URL**: [www.edernilson.com.br](http://www.edernilson.com.br)  
**Licença**: [The Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt)  
**Versão Corrente**: 1.0

Sobre o componente
-----

Componente baseado nos fontes do GMap, GMapRenderer e GMapInfoWinfow do primefaces 3.5, podendo assim utilizar os mesmos exemplos existentes [Showcase do primefaces](https://www.primefaces.org/showcase/ui/data/gmap/basic.xhtml).

Configuração Maven
---------

Adicione a dependência no seu `pom.xml`.
```xml
<dependency>
    <groupId>com.edernilson</groupId>
    <artifactId>leaflet-primefaces-extension</artifactId>
    <version>1.0</version>
</dependency>
```

Compatibilidade:
--------------

*leaflet-primefaces-extencion* foi escrito e testado com **PrimeFaces 3.5** e **JSF 2.2**.

Uso no JSF
------------

A biblioteca fornece uma taglib que inclui o componente `leaflet-primefaces-extencion`.
Para utilizar o componente, primeiro declare o namespace para taglib no arquivo fonte JSF (onde também deve ser incluído o namespace do PrimeFaces):

	<html xmlns="http://www.w3.org/1999/xhtml" lang="en"
		...
		xmlns:p="http://primefaces.org/ui"
		xmlns:e="http://www.edernilson.com/componentes/ui">
    
Em seguida, você pode usar a tag `leaflet` no seu arquivo de facelet exatamente como usaria com o componente GMAP do primefaces.

	<e:leaflet center="41.381542, 2.122893" zoom="15" type="HYBRID" style="width:100%;height:400px" />

Acesse o [Showcase do componente](www.edernilson.com.br/leaflet-extension-workshop) ou [Showcase do primefaces](https://www.primefaces.org/showcase/ui/data/gmap/basic.xhtml) para mais exemplos de uso.