# RoomExploration
Room exploration game implemented in Java and Swing


**[⭐️ Version Francais ⭐️](#Table-de-matières)**

**[⭐️ English Version ⭐️](##Table-of-content)**


## Table de matières 
- [Introduction & Utilisation](#introduction--utilisation)
- [Conception & Modélisation](#conception--modélisation)
- [Inplémentation](#inplémentation)
- [Annexe (UML)](#annexe-uml)


## Introduction & Utilisation
Ce projet s'inscrit dans le cadre du cours de Programmation Orientée Objet du quatrième
semestre de la Licence Informatique de l'Université de Strasbourg. Il se concentre principalement sur le jeu "Room Exploration". À partir du descriptif du projet, j'ai élaboré et réalisé le jeu en y intégrant des fonctionnalités personnalisées. L'implémentation a été réalisée en Java, avec la création de l'interface graphique réalisée en Swing.


#### Composition du jeu et règles
Le jeu met en scène à la fois des joueurs et des monstres. Les joueurs doivent avancer de salle en
salle jusqu'à atteindre la fin. Chaque niveau comporte deux types de monstres : les vampires et
les loups-garous. De plus, deux types d'objets sont présents : les trésors et les épées.

Les lettres correspondantes et leur signification dans le jeu:

| Keys     |  Signification      | 
|--------- |-------------------|
|    J     |   Joueur/Player       | 
|    V     |    Vampire| 
|    W     |    Loup-garou/Werewolf|
|    T     |    Tresor/Treasure    | 
|    E     |    Epee/Sword       | 
|    P     |    Porte/Door            |
|    S     |    Sortie/Exit        |


Les joueurs peuvent collecter des trésors et des épées pendant le jeu, les utiliser pour se défendre
ou tuer des monstres. Le score final est calculé en fonction du nombre d'objets collectés et de
l'attaque des monstres.

- Attaque de vampire: le joueur est obligé de revenir à la position de départ sans que les
objets ne soient rafraîchis.
- Attaque de loup-garou: mordre le joueur, réduisant ainsi les points de vie.
- Usage de trésor: restaurer une partie des points de vie du joueur.
- Usage d’épée: attaquer les monstres.


Il y a trois niveaux de jeu par défaut, le programme charge le modèle de jeu à partir du fichier template.txt. Pour configurer le jeu selon vos goûts, veuillez consulter la partie suivante : utilisation et configuration.


#### Utilisation & Configuration

- Lancer le jour:
    - Importer le projet dans IntelliJ ou Eclipse.
    - Exécutez le programme dans le package Test, l’interface du jeu apparaîtra automatiquement.
    - Vous pouvez ensuite suivre les instructions et jouer au jeu.

- Configuration: Dans le package Modèle, vous avez la possibilité d'ajuster les paramètres
du jeu selon l'expérience de jeu souhaitée dans le fichier Configuration.java. Ce dernier comprend divers éléments tels que le chemin du fichier du modèle de jeu, le nombre maximum de niveaux, la vitesse des monstres, les points de vie du joueur et des monstres, l'énergie des objets, ainsi que la puissance des monstres, entre autres.


## Conception & Modélisation

Pour structurer l'application graphique, j'ai opté pour l'architecture MVC (Modèle-Vue-
Contrôleur). Dans le package Modèle, j'ai implémenté toutes les classes métier du jeu. Le
package Vue constitue la partie chargée de présenter l'application à l'utilisateur et sur laquelle celui-ci interagit. Quant au package Contrôleur, il est dédié à l'interprétation des actions de l'utilisateur afin d'agir sur le modèle et la vue. De plus, un package d'Exceptions ainsi qu'un package de Tests ont également été inclus.

Voici la structure du package du projet :

- src
    - Modèle
    - Vue
    - Controleur
    - Exception
    - Test


#### Classes Métiers

Pour chaque élément du jeu j'ai créé une classe: **Joueur**, **Vampire**, **Werewolf**, **Epee**, **Tresor**. Au cours du processus de modélisation, j'ai utilisé diverses méthodes dans Patron Conception.

*Template method*

J’ai utilisé la méthode Template pour créer une classe avec une méthode qui réalise l’algorithme
commun en appellant des méthodes abstraites redéfinies dans ses sous-classes. Dans la classe
abstraite Monster, j'ai implémenté des méthodes concrètes (`move`, `isValidMove`, `detectPlayer`,
etc) et la fonction abstraite `Attack`. Différents monstres ont des actions différentes, donc les classes enfants (*Vampire* et *Werewolf*) étendent la classe *Monster* et implémentent ensuite la fonction `Attack`.

*Façade method*

J’ai utilisé la méthode Façade pour créer une classe intermédiaire entre les classes complexes et le client. Par exemple, la classe *GameControleur* est une classe intermédiaire entre les classes métiers et le client, elle est aussi une classe intermédiaire entre les classes d’interface graphique et le client, les classe métiers et les classes d’interface graphique. La complexité est encapsulée dans la façade. Dans la classe *Jeu*, on encapsule les méthodes plus complexe comme move de joueur et de monstre par des méthodes plus facils (`moveMonstre`, `movePlayer`, etc). Pareil pour la méthode instance initialiseGrid de *EnsDeSalles*, au lieu de gérer la complexité, le client peut créer un objet de Jeu directement.


*Observer Method*

J'ai utilisé la méthode Observer pour créer deux interfaces pour les observateurs :     *ChangementPositionListener* et *MonsterAttackListener*, qui héritent de l'interface *EventListener*. Chacune de ces interfaces contient une méthode prenant en argument un objet de type *ChangementPositionEvent* (`PositonChangee()`) et *MonsterAttackEvent* (`MonsterAttackEffect()`),
respectivement. La classe *Jeu* est observable. Les méthodes `PositionChangee()` et `MonsterAttackEffect()` peuvent être appelées à n'importe quel moment dans n'importe quelle méthode de la classe *Jeu*. En parallèle, la classe *VueGame* dans le package *Vue* implémente les interfaces *ChangementPositionListener* et *MonsterAttackListener*, ce qui lui permet d'écouter la classe métier et de réagir aux différents événements.

##### GUI
Dans ma conception du jeu, celui-ci est principalement divisé en cinq interfaces : accueil, règles du jeu, sélection de niveau, interface principale du jeu et fin. Pour cela, j'ai créé cinq classes qui héritent respectivement de JPanel : VueWelcome, VueTutorial, VueLevel, VueGame et VueTerminate. De plus, j'ai mis en place une classe GameUI qui hérite de JFrame et représente la fenêtre principale. À l'intérieur de cette classe, j’ai créé et changé les différents JPanel en fonction des actions de l'utilisateur (par exemple, en récupérant les choix du joueur via MouseListener). Ainsi, dans le GameControleur, il suffit simplement de créer une nouvelle instance de GameUI.

La classe VueGame implémente les interfaces ChangementPositionListener et
MonsterAttackListener, lui permettant d'écouter la classe métier et de réagir aux différents
événements. Lorsque le joueur clique sur un bouton pour interagir avec le jeu (déplacement,
récupération ou utilisation d'un objet), elle appelle la fonction correspondante dans la classe
GameControleur. Ensuite, cette fonction dans GameControleur notifie les classes métier pour
mettre à jour les statuts.

#### Controleur
Dans le Main.java du package Test, il est possible de créer directement un objet de GameControleur, puis d'appeler la fonction AfficheVues() pour démarrer le jeu. Ainsi, la classe GameControleur agit comme une interface entre les classes du modèle et les classes de la vue. Elle gère les interactions et les événements entre les deux packages : les données des classes métier sont observées par les classes d'interface graphique (écouteurs), et ces écouteurs envoient leurs événements au contrôleur. Celui-ci met à jour et lance des traitements pour les classes du
modèle et de la vue. Idéalement, les classes du modèle ne dépendent pas des classes de la vue.




## Inplémentation

Afin d'améliorer l'implémentation de ce jeu, j'ai utilisé des concepts Java tels que le polymorphisme, l'encapsulation et l'héritage. J'ai créé les interfaces Objet et Vivant pour les classes Joueur et Monstre, Trésor et Épée, qui partagent des caractéristiques communes ainsi que certaines fonctions similaires. Pour faciliter l'implémentation et accroître la flexibilité, j'ai également introduit des énumérations pour Keys, Directions, Jeton, ainsi que Musique. Ainsi, si l'on souhaite modifier des paramètres ou ajouter de nouvelles fonctionnalités à l'avenir, cela sera plus facile à réaliser.

*Threads*

Au cours du processus d'implémentation du code, j'ai également constaté la nécessité d'utiliser
des Threads pour paralléliser les entrées du joueur avec les actions du monstre. Ainsi, dans la
fonction startGame, j'ai créé un thread pour gérer le déplacement et les actions des monstres. De
plus, dans la fonction showGame de la classe GameUI, j'ai mis en place un autre thread pour
paralléliser l'interface graphique avec les fonctionnalités métier.


*Actions de Monstres*


Avant que chaque monstre n'attaque, il doit d'abord détecter la présence d'un joueur à proximité.
De la même manière, lorsqu'un joueur collecte des objets ou attaque des monstres, il doit
également détecter s'il y a des objets ou des monstres à proximité. Pour ce faire, j'ai mis en place
plusieurs équations auxiliaires permettant de détecter la position des joueurs ou des monstres.





## Annexe (UML)

Le diagramme UML global du projet:
 ![UML global](assets/Diagram_Overall.png) 




## Table of content
- [Introduction & Usage](#introduction--usage)
- [Design & Modelization](#design--modelization)
- [Inplementation](#inplementation)
- [Annex (UML)](#annex-uml)


## Introduction & Usage

## Design & Modelization

## Inplementation

## Annex (UML)

