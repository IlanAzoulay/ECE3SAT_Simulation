# ECE3SAT_Simulation (ENGLISH)

*French version below*

*Author: Ilan Azoulay, ECE3SAT ADCS 2018-2019*


## Objectives

- Put the cubesat in a simulation recreating the conditions under which it is going to be during the mission
- Manually define an angular speed
- Enable communication between the simulation and the real Cubesat's Arduino in order to test the stabilization algorithm (REMINDER: the cubesat must always have the tether point towards the Earth)


## Origins


This program was created as part of an End Studies Project at ECE Paris, module "Attitude Determination Control System", school year 2018-2019. The end goal is to simulate the Cubesat's situation when it is in space, and thereby check its stabilization algorithm by connecting it to the simulation.

A previous version had been created the previous year on the software "Processing". This version didn't work well, had zero documentation, and its author would not bother to answer questions. We therefore decided to create a new simulation, on Eclipse so it can be more accessible for future years' student who will continue the project, and with the LWJGL3 / OpenGL graphics library.

You will find all the documentation below, and also within the comments of the java code.

Here's the tutorial playlist I followed to build the engine: https://www.youtube.com/playlist?list=PLaWuTOi9sDepAlbNEOXhjjFSo2WBkWOT2

If this is still not enough, feel free to contact me.


## Beginning

- Install Eclipse (Java IDE)
- Clone this project and import it on Eclipse
- Install LWJGL3 and configure the external libraries. I advise you to follow the tutorial like I did:  https://youtu.be/ISWcMXxl26s
- Install WindowBuilder for Eclipse (optional but will make your life easier). It's an add-on for the IDE, and very easy to add. Follow this tutorial: https://www.youtube.com/watch?v=l0DvVgqy_q8
- Install the RXTX library for Arduino communication

Note: Do not forget to put packages in hierarchal view. Otherwise the project will look messy. Top-right corner of the "Package explorer" window (left side of the screen), you will find a little arrow facing down. Click it, then Package presentation -> Hierarchical



## Summary

Once the simulation is launched, you will find 2 main elements on the main window. Earth, and a weirdly textured cube.

I worked pretty hard to make the cube look like this, and for good reasons. You will see 2 main faces:
- One yellow face with a big T: T for Tether, or the French for Earth (Terre). Whichever you like best, it's meant to point towards the Earth.
- One green face with a nice V: If the cubesat shows you this face, then all is fine. It's the face opposite of the Tether, and since our point of view is towards Earth, seeing this face onscreen means the cube has the T face pointing towards Earth, and so it's all good man.


## Architecture of the project

The "main" file of the project, its central piece, is the "MainClass" class, in the "mainPackage" package.
 
The "engine" package contains everything useful in order to make the simulation run.

I advise you against touching this package. It contains a lot of code that's just brainlessly copy-pasted from the tutorial, that I only half-understand. The only thing to know is that it works.

We can consider the "engine" package as the low-level code. The modifications to add will be high-level.

You will find the textures in the foldier res/textures/ , and the 3D models in res/models/

The "myModels" package contains all elements added by us for the simulation. It features so far only Earth and the Cubesat. They both inherit from the "ModelByMe" class, so they can have common functions, which can be useful some day.

All is at the right scale in terms of size and time. For example, Earth will make a full 360 on itself in 24 hours in this simulation. And the cubesat goes at the speed of the ISS relatively to Earth.

A few exceptions were made for esthetic reasons, however. Most notably, the Cubesat, was made slightly bigger, otherwise the size difference compared to Earth would be too much for the simulation. Its shown altitude was also increased on the screen, but it does not affect the calculations which still only read the real altitude. These exceptions must not have any effect on what we're testing.


## Important stuff to know

- X: Horizontal axis (left to right)
- Y: Vertical axis (Top to bottom)
- Z: Depth axis (from your eyes to the depth of the screen)

### Useful methods

- *setPosition(x, y, z)* : Fixes the object at the coordonates (x, y, z)

- *addPosition(x, y, z)* : Adds (x, y, z) to the current position of the object. NOTE: It's a position and not a speed. If you want to move the object, you'll have to repeat this method at every iteration of the main while. 

- *setRotation(x, y, z)* : Fixes the object at x degrees around the horizontal axis, y degrees around the vertical axis, and z degrees around the depth axis.

- *addRotation(x, y, z)* : Adds (x, y, z) to the current rotation of the object. NOTE: It's a position and not a speed. If you want to move the object, you'll have to repeat this method at every iteration of the main while. 


NOTE: Depending on the object, you might want to call the entity of the object: Objet.getEntity().setPosition(x, y, z)

NOTE2: In most cases, you can also enter a Vector3f instead (x, y, z) as parameter. I made sure both work (Overloading)



### To import 3D models

- Make sure its extension is .obj

- IMPORTANT: Make sure the faces are TRIANGULAR. Otherwise, look up how to "triangulate" your model / your model's faces depending on the 3D software you're using

- I really advise AGAINST using Blender. The files created on Blender are exported with "vt" (aka "UV Unwrapping"), without which the simulation cannot use them. I personally prefer using Cinema4D, which causes no problem, and it's free for student. If you ABSOLUTELY want to go through Blender because your parents didn't give you enough care and that you like to hurt yourself, be sure to watch a tutorial on how to do UV Unwrapping. But don't be surprised if the textures look like shit afterwards. 



---------------------------------------------------------

# ECE3SAT_Simulation (FRANCAIS)

*Auteur: Ilan Azoulay. PFE 1840, ECE3SAT ADCS 2018-2019.*

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
- Installer WindowBuilder sur Eclipse. C'est un add-on de l'IDE, et donc très facile à ajouter. Suivre ce tutoriel: https://www.youtube.com/watch?v=l0DvVgqy_q8
- Installer la librairie RXTX pour la communication Arduino

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

- X: axe horizontal (gauche à droite)
- Y: axe vertical (haut en bas)
- Z: axe de profondeur (va de toi vers le fond de l'écran)

### Fonctions utiles

- *setPosition(x, y, z)* : Fixe l'objet à la position (x, y, z)

- *addPosition(x, y, z)* : Ajoute (x, y, z) à la position actuelle de l'objet. NOTE: Il s'agit d'une position et non d'une vitesse. Si vous voulez déplacer un objet, cette commande devra être répétée à chaque itération du while. 

- *setRotation(x, y, z)* : Donne à l'objet une tournure d'angle x degrés autour de l'axe horizontal, y degrés autour de l'axe vertical, et z degrés autour de l'axe profondeur

- *addRotation(x, y, z)* : Ajoute (x, y, z) degrés à la tournure actuelle de l'objet. NOTE: Il s'agit d'une tournure et non d'une vitesse angulaire. Si vous voulez faire tourner un objet, cette commande devra être répétée à chaque itération du while.


NOTE: En fonction de l'objet, vous devrez probablement appeler l'entité de l'objet: Objet.getEntity().setPosition(x, y, z)

NOTE2: Dans la plupart des cas, vous pouvez aussi entrer un Vector3f au lieu de (x, y, z). J'ai fait en sorte que les 2 marchent (Overloading)


### Pour importer des modèles 3D

- Vérifier qu'il s'agit d'un format .obj

- IMPORTANT: Vérifier que les faces du modèles sont TRIANGULAIRES. Si ce n'est pas le cas, regardez comment "triangulate" le modèle/les faces du modèles en fonction de votre logiciel 3D.

- Je vous déconseille fortement de passer par Blender. Les fichiers créés sur Blender ne sont pas exportés avec des "vt" (aka "UV unwrapping"), sans quoi la simulation LWJGL ne peut pas les utiliser. Personnellement, je préfère Cinema 4D, qui ne pose aucun problème, et qui est gratuit pour les étudiants. Si vous voulez ABSOLUMENT passer par Blender parce que vos parents ne vous ont pas donné assez d'attention et que vous aimez vous faire du mal, pensez à regarder un tutoriel pour comment faire du UV Unwrapping. Mais ne vous étonnez pas ensuite si les textures ressemblent à rien ensuite.




## POUR LES ANNEES SUIVANTES: Que faut-il encore rajouter ?

- [X] Essayer d'améliorer l'Anti-Aliasing (purement esthétique)
- [ ] Communication Arduino
- [ ] Comprendre l'algorithme Arduino / Faire une documentation / Refaire le programme
- [X] Rajouter des champs de texte pour modifier la vitesse angulaire du CubeSat
- [ ] Rajouter le soleil
- [ ] Rajouter les capteurs, et tous ces trucs là
