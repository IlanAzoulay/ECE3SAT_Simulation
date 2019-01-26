# ECE3SAT_Simulation

*Auteur: Ilan Azoulay. PFE 1840, ECE3SAT ADCS 2018-2019.*

[NOTE POUR MOI-MÊME: Finir ce ReadMe]

Ce programme a été créé pour le Projet de Fin d'Etudes CubeSat de l'ECE Paris, module "Attitude Determination Control System", de l'année scolaire 2018-2019. Le but final est de simuler la situation du Cubesat lorsqu'il sera dans l'espace, et ainsi tester l'algorithme de stabilisation en le connectant à cette simulation.

Un version précédente a été créée l'année d'avant sur le logiciel Processing. Cette version ne marchait pas bien, n'avait aucune documentation, et son auteur ne répondait pas aux questions. Il a donc été décidé de créer une nouvelle simulation, sur Eclipse pour être plus accessible pour les étudiants reprenant le projet les années suivantes, avec la librairie LWJGL3 / OpenGL.

Vous trouverez la documentation ci-dessous, ainsi que dans les commentaires du projet.

Voici la playlist du tutoriel que j'ai suivi: https://www.youtube.com/playlist?list=PLaWuTOi9sDepAlbNEOXhjjFSo2WBkWOT2

Si tout cela ne suffit pas, n'hésitez pas à me contacter par Facebook.


## Débuter

- Installer Eclipse (IDE pour Java)
- Télécharger le DOSSIER "LWJGL3D_Try1", et l'importer dans Eclipse
- Installer LWJGL 3 et configurer les librairies externes. Je conseille de suivre le tutoriel que j'ai utilisé pour ça: https://youtu.be/ISWcMXxl26s


Oui, c'est tout :)

## Architecture du projet

Le "main" du projet, sa pièce centrale, est la classe "MainClass", dans le package "mainPackage".

Le package "engine" contient tout ce qui sert à faire tourner la simulation. 

Je vous conseille de ne pas trop toucher au package "engine". C'est surtout du code recopié depuis le tutoriel, que je ne comprends qu'à moitié. L'important, c'est que ça marche, et que ça fait tourner la simulation. On va se servir de ce package comme outils pour créer notre propre truc, mais en dehors de ce package.

Vous trouverez les textures dans le dossier res/textures/



## Trucs importants à savoir

### Pour importer des modèles 3D

- Vérifier qu'il s'agit d'un format .obj

- IMPORTANT: Vérifier que les faces du modèles sont TRIANGULAIRES. Si ce n'est pas le cas, regardez comment "triangulate" le modèle/les faces du modèles en fonction de votre logiciel 3D.

- Je vous déconseille fortement de passer par Blender. Les fichiers créés sur Blender ne sont pas exportés avec des "vt" (aka "UV unwrapping"), sans quoi la simulation LWJGL ne peut pas les utiliser. Personnellement, je préfère Cinema 4D, qui ne pose aucun problème, et qui est gratuit pour les étudiants. Si vous voulez ABSOLUMENT passer par Blender parce que vos parents ne vous ont pas donné assez d'attention et que vous aimez vous faire du mal, pensez à regarder un tutoriel pour comment faire du UV Unwrapping. Mais ne vous étonnez pas ensuite si les textures ressemblent à rien ensuite.
