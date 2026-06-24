# README para aula:
Nome: Sofia Etchepare Daronco  
Curso: Sistemas de Informação  
Proposta: Permitir o uso de https://learn.microsoft.com/en-us/entra/identity-platform/v2-oauth2-auth-code-flow para autenticação, assim como corrigir a compatibilidade com log4j.  
Referências: N/A  
Resultado:  
TODO

8/6:  
Atualmente, a funcionalidade básica do interactive flow está funcionando (assim como o device flow, que já era parte do projeto). Ainda faltam alguns aspectos importantes para resolver:  
~~Reduzir o uso de static, especialmente em MicrosoftAuth.java~~  
~~Completar o processo de relocação em~~ https://github.com/elc117/final-2026a-kitty/blob/2b6bf8f25eff0c0347baa47b98e5fae71480c2aa/build.gradle.kts#L213  

Considerações para a correção:  
A natureza desse projeto necessita que seja estruturado de uma maneira que pode parecer estranha, por exemplo a falta de lambdas. Lambdas não podem ser utilizadas pois o bytecode emitido deve ser compatível com Java 6, essa compatibilidade é necessária para reduzir o tamanho dos binary patches gerados.  
Como as mudanças ao projeto sendo utilizadas como trabalho ainda estão em desenvolvimento, as instruções abaixo de Development devem ser utilizadas, ao invés de Usage. Especificamente:  
1. Utilizar Java 8 + JavaFX
2. Rodar a tarefa filterDecomp com Gradle
3. Rodar a tarefa runLauncher com Gradle

# README original:
# MSA4Legacy
Patches for the old official Minecraft launcher to add Microsoft account support  

# Requirements  

These patches bump the Java version requirement from Java 6 + JavaFX to Java 8 + JavaFX, the Java version I use for development can be found [here](https://cdn.azul.com/zulu/bin/zulu8.60.0.21-ca-fx-jdk8.0.322-linux_x64.tar.gz).  

# Usage  
1. Download the installer and patches from the Releases tab
2. Put the patches file in the same directory as the official launcher jar, which can be found at https://launcher.mojang.com/v1/objects/eabbff5ff8e21250e33670924a0c5e38f47c840b/launcher.jar  
3. Run the installer and select the official launcher jar  
4. The patched jar will be in that same directory, but called "launcher-patched.jar"

# Development  
This project requires jdk8 due to a library, however to minimize binpatches any development must be done targeting Java 6 bytecode, so Java 6 syntax must be used.  

To set up a decompiled environment, run the filterDecomp task  
To create patches with your changes, run the genSourceDiffs task  
To create a recompiled jar, run the jarLauncher task (DOES NOT KEEP UNSAVED PATCHES)  
To generate binary patches to use with the installer, run the genBinPatches task  

More tasks can be found in the build.gradle.kts file, please read which tasks depend on others to prevent unsaved work being lost.  

# Other fixes  
Auth unrelated fixes can be found at Other fixes.txt  

# Is this legal?  
Yes, special care has been taken to keep this project 100% legal, by using both binary and source patches, no substantial mojang code is distributed by this project

I AM NOT AFFILIATED WITH NOR ENDORSED BY Microsoft Corporation NOR ITS SUBSIDIARY Mojang Studios IN ANY WAY  
