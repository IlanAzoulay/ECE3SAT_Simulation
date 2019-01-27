# ECE3SAT_Simulation

*Auteur: Ilan Azoulay. PFE 1840, ECE3SAT ADCS 2018-2019.*

[NOTE POUR MOI-MÊME: Finir ce ReadMe]

## Objectif

- Mettre le cubeSat dans une simulation imitant les conditions dans lesquelles il sera pendant la mission
- Définir manuellement une vitesse angulaire
- Faire communiquer la simulation avec l'Arduino du CubeSat pour tester s'il est capable de se stabiliser (RAPPEL: le CubeSat doit toujours avoir la face ayant le Tether pointant vers la Terre)


## Origins

Ce programme a été créé pour le Projet de Fin d'Etudes CubeSat de l'ECE Paris, module "Attitude Determination Control System", de l'année scolaire 2018-2019. Le but final est de simuler la situation du Cubesat lorsqu'il sera dans l'espace, et ainsi tester l'algorithme de stabilisation en le connectant à cette simulation.

Un version précédente a été créée l'année d'avant sur le logiciel Processing. Cette version ne marchait pas bien, n'avait aucune documentation, et son auteur ne répondait pas aux questions. Il a donc été décidé de créer une nouvelle simulation, sur Eclipse pour être plus accessible pour les étudiants reprenant le projet les années suivantes, avec la librairie LWJGL3 / OpenGL.

Vous trouverez la documentation ci-dessous, ainsi que dans les commentaires du projet.

Voici la playlist du tutoriel que j'ai suivi: https://www.youtube.com/playlist?list=PLaWuTOi9sDepAlbNEOXhjjFSo2WBkWOT2

Si tout cela ne suffit pas, n'hésitez pas à me contacter par Facebook.


## Débuter

- Installer Eclipse (IDE pour Java)
- Cloner ce projet et l'importer dans Eclipse
- Installer LWJGL 3 et configurer les librairies externes. Je conseille de suivre le tutoriel que j'ai utilisé pour ça: https://youtu.be/ISWcMXxl26s

Note: Pensez à mettre les packages en vue hiérarchique. Sinon le projet risque de ressembler à rien. En haut à droite de la fenêtre "Package explorer" (gauche de l'écran), vous avez une petite flèche allant vers le bas. Cliquez dessus, puis Package presentation -> Hierarchical



## En gros

Une fois que la simulation est lancée, vous verrez 2 éléments principaux: la Terre, et un cube avec une texture bizarre.

J'ai bien galéré à lui faire coller cette texture, et c'est pas pour rien. Vous avez 2 faces qui ressortent:
- Une face jaune avec un grand T: T comme Terre ou Tether, quoi qu'il en soit, c'est la face qui doit pointer vers la Terre
- Une face verte avec un joli V: Si le cubeSat vous montre cette face, c'est que tout va bien. C'est la face opposée au Tether, et comme notre vue pointe vers la Terre, le fait de voir cette face veut dire que le cube pointe la face T vers la Terre, et donc que tout va bien.


## Architecture du projet

Le "main" du projet, sa pièce centrale, est la classe "MainClass", dans le package "mainPackage".

Le package "engine" contient tout ce qui sert à faire tourner la simulation. 

Je vous conseille de ne pas trop toucher au package "engine". Il contient beaucoup de code recopié bêtement depuis le tutoriel, que je ne comprends qu'à moitié. L'important, c'est que ça marche, et que ça fait tourner la simulation. 

On peut considérer ce package "engine" comme le bas-niveau. Le reste des modifications sera à faire en haut-niveau.

Vous trouverez les textures dans le dossier res/textures/ , et les modèles 3D dans res/models/

Le package myModels contient les éléments ajoutés par nous à la simulation. Ceci contient donc pour l'instant juste la Terre et le CubeSat. Ils héritent tous deux de la classe ModelByMe, histoire qu'ils aient des fonctions communes, ce qui peut être utile.

Tout est à bonne échelle, en taille et en temps. Par exemple, la Terre fait un tour sur elle-même en 24h dans cette simulation. Et le cubeSat va à la vitesse de l'ISS par rapport à la Terre.

Quelques exceptions ont cependant été faites par esthétisme. Notamment le cubeSat, légèrement agrandi, autrement la différence de taille par rapport à la Terre aurait été trop importante pour la simulation. Son altitude a aussi été augmentée, par raisons purement esthétiques. Ces exceptions ne doivent cependant avoir aucun effet sur ce que nous devons tester.


## Trucs importants à savoir

### Pour importer des modèles 3D

- Vérifier qu'il s'agit d'un format .obj

- IMPORTANT: Vérifier que les faces du modèles sont TRIANGULAIRES. Si ce n'est pas le cas, regardez comment "triangulate" le modèle/les faces du modèles en fonction de votre logiciel 3D.

- Je vous déconseille fortement de passer par Blender. Les fichiers créés sur Blender ne sont pas exportés avec des "vt" (aka "UV unwrapping"), sans quoi la simulation LWJGL ne peut pas les utiliser. Personnellement, je préfère Cinema 4D, qui ne pose aucun problème, et qui est gratuit pour les étudiants. Si vous voulez ABSOLUMENT passer par Blender parce que vos parents ne vous ont pas donné assez d'attention et que vous aimez vous faire du mal, pensez à regarder un tutoriel pour comment faire du UV Unwrapping. Mais ne vous étonnez pas ensuite si les textures ressemblent à rien ensuite.




## POUR LES ANNEES SUIVANTES: Que faut-il encore rajouter ?

- [ ] Essayer d'améliorer l'Anti-Aliasing (purement esthétique)
- [ ] Communication Arduino
- [ ] Comprendre l'algorithme Arduino / Faire une documentation / Refaire le programme
- [ ] Rajouter des champs de texte pour modifier la vitesse angulaire du CubeSat
- [ ] Rajouter le soleil
- [ ] Rajouter les capteurs, et tous ces trucs là
