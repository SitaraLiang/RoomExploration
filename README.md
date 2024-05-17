# RoomExploration
Room exploration game implemented in Java and Swing


**[⭐️ Version Francais ⭐️](#Table-de-matières)**

**[⭐️ English Version ⭐️](#Table-of-content)**


## Table de matières 
- [Introduction & Utilisation](#introduction--utilisation)
- [Conception & Modélisation](#conception--modélisation)
- [Inplémentation](#inplémentation)
- [Annexe (UML)](#annexe-uml)


## Introduction & Utilisation
Ce projet s'inscrit dans le cadre du cours de Programmation Orientée Objet du quatrième
semestre de la Licence Informatique de l'Université de Strasbourg. Il se concentre principalement sur le jeu "Room Exploration". À partir du descriptif du projet, j'ai élaboré et réalisé le jeu en y intégrant des fonctionnalités personnalisées. L'implémentation a été réalisée en Java, avec la création de l'interface graphique réalisée en Swing.


#### Composition du jeu et règles
Le jeu met en scène à la fois des joueurs et des monstres. Les joueurs doivent avancer de salle en salle jusqu'à atteindre la fin. Chaque niveau comporte deux types de monstres : les vampires et les loups-garous. De plus, deux types d'objets sont présents : les trésors et les épées.

Les lettres correspondantes et leur signification dans le jeu:

| Keys     |  Signification      | 
|--------- |-------------------|
|    J     |   Joueur       | 
|    V     |    Vampire| 
|    W     |    Loup-garou|
|    T     |    Tresor  | 
|    E     |    Epee      | 
|    P     |    Porte           |
|    S     |    Sortie        |


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

- Configuration: Dans le package Modèle, vous avez la possibilité d'ajuster les paramètres du jeu selon l'expérience de jeu souhaitée dans le fichier Configuration.java. Ce dernier comprend divers éléments tels que le chemin du fichier du modèle de jeu, le nombre maximum de niveaux, la vitesse des monstres, les points de vie du joueur et des monstres, l'énergie des objets, ainsi que la puissance des monstres, entre autres.


## Conception & Modélisation

Pour structurer l'application graphique, j'ai opté pour l'architecture MVC (Modèle-Vue-Contrôleur). Dans le package Modèle, j'ai implémenté toutes les classes métier du jeu. Le package Vue constitue la partie chargée de présenter l'application à l'utilisateur et sur laquelle celui-ci interagit. Quant au package Contrôleur, il est dédié à l'interprétation des actions de l'utilisateur afin d'agir sur le modèle et la vue. De plus, un package d'Exceptions ainsi qu'un package de Tests ont également été inclus.

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

J’ai utilisé la méthode Template pour créer une classe avec une méthode qui réalise l’algorithme commun en appellant des méthodes abstraites redéfinies dans ses sous-classes. Dans la classe abstraite Monster, j'ai implémenté des méthodes concrètes (`move`, `isValidMove`, `detectPlayer`, etc) et la fonction abstraite `Attack`. Différents monstres ont des actions différentes, donc les classes enfants (*Vampire* et *Werewolf*) étendent la classe *Monster* et implémentent ensuite la fonction `Attack`.

*Façade method*

J’ai utilisé la méthode Façade pour créer une classe intermédiaire entre les classes complexes et le client. Par exemple, la classe *GameControleur* est une classe intermédiaire entre les classes métiers et le client, elle est aussi une classe intermédiaire entre les classes d’interface graphique et le client, les classe métiers et les classes d’interface graphique. La complexité est encapsulée dans la façade. Dans la classe *Jeu*, on encapsule les méthodes plus complexe comme move de joueur et de monstre par des méthodes plus facils (`moveMonstre`, `movePlayer`, etc). Pareil pour la méthode instance initialiseGrid de *EnsDeSalles*, au lieu de gérer la complexité, le client peut créer un objet de Jeu directement.


*Observer Method*

J'ai utilisé la méthode Observer pour créer deux interfaces pour les observateurs: *ChangementPositionListener* et *MonsterAttackListener*, qui héritent de l'interface *EventListener*. Chacune de ces interfaces contient une méthode prenant en argument un objet de type *ChangementPositionEvent* (`PositonChangee()`) et *MonsterAttackEvent* (`MonsterAttackEffect()`),
respectivement. La classe *Jeu* est observable. Les méthodes `PositionChangee()` et `MonsterAttackEffect()` peuvent être appelées à n'importe quel moment dans n'importe quelle méthode de la classe *Jeu*. En parallèle, la classe *VueGame* dans le package *Vue* implémente les interfaces *ChangementPositionListener* et *MonsterAttackListener*, ce qui lui permet d'écouter la classe métier et de réagir aux différents événements.

#### GUI
Dans ma conception du jeu, celui-ci est principalement divisé en cinq interfaces : accueil, règles du jeu, sélection de niveau, interface principale du jeu et fin. Pour cela, j'ai créé cinq classes qui héritent respectivement de JPanel : VueWelcome, VueTutorial, VueLevel, VueGame et VueTerminate. De plus, j'ai mis en place une classe GameUI qui hérite de JFrame et représente la fenêtre principale. À l'intérieur de cette classe, j’ai créé et changé les différents JPanel en fonction des actions de l'utilisateur (par exemple, en récupérant les choix du joueur via MouseListener). Ainsi, dans le GameControleur, il suffit simplement de créer une nouvelle instance de GameUI.

La classe VueGame implémente les interfaces ChangementPositionListener et MonsterAttackListener, lui permettant d'écouter la classe métier et de réagir aux différents événements. Lorsque le joueur clique sur un bouton pour interagir avec le jeu (déplacement, récupération ou utilisation d'un objet), elle appelle la fonction correspondante dans la classe GameControleur. Ensuite, cette fonction dans GameControleur notifie les classes métier pour mettre à jour les statuts.

#### Controleur
Dans le Main.java du package Test, il est possible de créer directement un objet de GameControleur, puis d'appeler la fonction AfficheVues() pour démarrer le jeu. Ainsi, la classe GameControleur agit comme une interface entre les classes du modèle et les classes de la vue. Elle gère les interactions et les événements entre les deux packages : les données des classes métier sont observées par les classes d'interface graphique (écouteurs), et ces écouteurs envoient leurs événements au contrôleur. Celui-ci met à jour et lance des traitements pour les classes du modèle et de la vue. Idéalement, les classes du modèle ne dépendent pas des classes de la vue.




## Inplémentation

Afin d'améliorer l'implémentation de ce jeu, j'ai utilisé des concepts Java tels que le polymorphisme, l'encapsulation et l'héritage. J'ai créé les interfaces Objet et Vivant pour les classes Joueur et Monstre, Trésor et Épée, qui partagent des caractéristiques communes ainsi que certaines fonctions similaires. Pour faciliter l'implémentation et accroître la flexibilité, j'ai également introduit des énumérations pour Keys, Directions, Jeton, ainsi que Musique. Ainsi, si l'on souhaite modifier des paramètres ou ajouter de nouvelles fonctionnalités à l'avenir, cela sera plus facile à réaliser.

*Threads*

Au cours du processus d'implémentation du code, j'ai également constaté la nécessité d'utiliser des Threads pour paralléliser les entrées du joueur avec les actions du monstre. Ainsi, dans la fonction startGame, j'ai créé un thread pour gérer le déplacement et les actions des monstres. De plus, dans la fonction showGame de la classe GameUI, j'ai mis en place un autre thread pour paralléliser l'interface graphique avec les fonctionnalités métier.


*Actions de Monstres*


Avant que chaque monstre n'attaque, il doit d'abord détecter la présence d'un joueur à proximité. De la même manière, lorsqu'un joueur collecte des objets ou attaque des monstres, il doit également détecter s'il y a des objets ou des monstres à proximité. Pour ce faire, j'ai mis en place plusieurs équations auxiliaires permettant de détecter la position des joueurs ou des monstres.





## Annexe (UML)

Le diagramme UML global du projet:
 ![UML global](assets/Diagram_Overall.png) 




## Table of content
- [Introduction & Usage](#introduction--usage)
- [Design & Modelization](#design--modelization)
- [Inplementation](#inplementation)
- [Annex (UML)](#annex-uml)


## Introduction & Usage

This project is part of the Object Oriented Programming course of the fourth semester of the Computer Science Degree at the University of Strasbourg. It mainly focuses on the game "Room Exploration". Based on the project description, I developed and created the game by integrating personalized features. The implementation was carried out in Java, with the creation of the graphical interface carried out in Swing.


#### Game composition and rules

The game features both players and monsters. Players must advance from room to room until they reach the end. Each level has two types of monsters: vampires and werewolves. In addition, two types of items are present: treasures and swords.

The corresponding letters and their meaning in the game:


| Keys     |  Signification      | 
|--------- |-------------------|
|    J     |   Player       | 
|    V     |   Vampire| 
|    W     |    Werewolf|
|    T     |    Treasure    | 
|    E     |    Sword       | 
|    P     |    Door            |
|    S     |    Exit        |


Players can collect treasures and swords during gameplay, use them to defend themselves or kill monsters. The final score is calculated based on the number of items collected and the attack of the monsters.

- Vampire attack: the player is forced to return to the starting position without the objects are refreshed.
- Werewolf attack: bite the player, reducing health points.
- Use of treasure: restore part of the player's life points.
- Use of sword: attack monsters.


There are three game levels by default, the program loads the game template from the template.txt file. To configure the game according to your tastes, please see the following part: use and configuration.


#### Usage & Configuration

- Start the day:
     - Import the project into IntelliJ or Eclipse.
     - Run the program in the Test package, the game interface will appear automatically.
     - You can then follow the instructions and play the game.

- Configuration: In the Template package you have the option to adjust the settings of the game according to the desired gaming experience in the Configuration.java file. The latter includes various elements such as the path of the game model file, the maximum number of levels, the speed of the monsters, the hit points of the player and the monsters, the energy of the objects, as well as the power of the monsters, among others.


## Design & Modeling

To structure the graphical application, I opted for the MVC architecture (Model-View-Controller). In the Model package, I implemented all the model classes of the game. Vue package constitutes the part responsible for presenting the application to the user and on which the latter interacts. As for the Controller package, it is dedicated to interpreting user actions in order to act on the model and the view. Additionally, an Exceptions package as well as a Tests package have also been included.

Here is the structure of the project package:

- src
     - Model
     - View
     - Controller
     - Exception
     - Test

#### Model Classes

For each element of the game I created a class: **Player**, **Vampire**, **Werewolf**, **Sword**, **Treasure**. During the modeling process, I used various methods in Patron Conception.

*Template methods*

I used the Template method to create a class with a method that carries out the algorithm common by calling abstract methods redefined in its subclasses. In the classroom abstract Monster, I implemented concrete methods (`move`, `isValidMove`, `detectPlayer`, etc) and the abstract function `Attack`. Different monsters have different actions, so the child classes (*Vampire* and *Werewolf*) extend the *Monster* class and then implement the `Attack` function.

*Method facade*

I used the Façade method to create an intermediate class between the complex classes and the client. For example, the *GameControleur* class is an intermediate class between the business classes and the client, it is also an intermediate class between the graphical interface classes and the client, the model classes and the graphical interface classes. The complexity is encapsulated in the facade. In the *Game* class, we encapsulate more complex methods like player and monster moves with easier methods (`moveMonstre`, `movePlayer`, etc). Same for the initializeGrid instance method of *EnsDeSalles*, instead of managing the complexity, the client can create a Game object directly.

*Observer Method*

I used the Observer method to create two interfaces for observers: *ChangePositionListener* and *MonsterAttackListener*, which inherit from the *EventListener* interface. Each of these interfaces contains a method taking as argument an object of type *ChangementPositionEvent* (`PositonChangee()`) and *MonsterAttackEvent* (`MonsterAttackEffect()`), respectively. The *Game* class is observable. The `PositionChangee()` and `MonsterAttackEffect()` methods can be called at any time in any method of the *Game* class. In parallel, the *VueGame* class in the *Vue* package implements the *ChangePositionListener* and *MonsterAttackListener* interfaces, which allows it to listen to the model class and react to different events.

#### GUI

In my conception of the game, it is mainly divided into five interfaces: home, game rules, level selection, main game interface and ending. For this, I created five classes which inherit respectively from JPanel: VueWelcome, VueTutorial, VueLevel, VueGame and VueTerminate. Additionally, I implemented a GameUI class that inherits from JFrame and represents the main window. Inside this class, I created and changed the different JPanels based on user actions (for example, retrieving player choices via MouseListener). So, in the GameControleur, all we have to do is create a new GameUI instance.

The VueGame class implements the ChangePositionListener and ChangementPositionListener interfaces. MonsterAttackListener, allowing it to listen to the model class and react to different events. When the player clicks a button to interact with the game (moving, retrieval or use of an object), it calls the corresponding function in the class GameController. Then this function in GameControleur notifies the classes of for update statuses.

#### Controller
In the Main.java of the Test package, it is possible to directly create a GameController object, then call the DisplayViews() function to start the game. Thus, the GameController class acts as an interface between the model classes and the sight classes. It manages interactions and events between the two packages: data from model classes is observed by GUI classes (listeners), and these listeners send their events to the controller. This updates and launches processing for the classes of the model and view. Ideally, the model classes do not depend on the view classes.

## Inplementation
In order to improve the implementation of this game, I used Java concepts such as polymorphism, encapsulation and inheritance. I created the *Object* and *Living* interfaces for the Player and Monster, Treasure and Sword classes, which share common characteristics as well as some similar functions. To make implementation easier and increase flexibility, I also introduced enums for Keys, Directions, Token, as well as Music. This way, if one wants to change settings or add new features in the future, it will be easier to do so.

*Threads*

During the process of implementing the code, I also observed the need to use Threads to parallelize the player's inputs with the monster's actions. So, in the startGame function, I created a thread to manage the movement and actions of the monsters. Additionally, in the showGame function of the GameUI class, I set up another thread to parallelize the GUI with the model functionalities.


*Monster Actions*

Before each monster attacks, it must first detect the presence of a nearby player. Similarly, when a player collects items or attacks monsters, they must also detect if there are any items or monsters nearby. To do this, I implemented several auxiliary equations to detect the position of players or monsters.

## Annex (UML)

The overall UML diagram of the project:
  ![UML global](assets/Diagram_Overall.png)
