# ROBI

### Principe
Le but est de déplacer Robi qui est un personnage (representé par un quadrilatère) à l'aide de commandes.
<br />
Les commandes ressemblent au langage LISP
Un parseur est chargé de recupérer les commandes entrées par l'utilisateur et les renvoie sous forme d'expréssions.<br/>

##### Un petit exemple

`
(script
	(space 	color 		black)
	(robi 	color		white)
	(robi	translate	10 20)
	(robi	sleep		100)
	(robi	translate	12 30)
)
`
<br />
Ce script va créer un fond noir, un robi blanc (waw, super) <br />
Puis va le déplacer en faisant une translation de ses coordonnées. On fait une pause de 100 ms puis on le déplace encore.
<br />
Ce qui fait une très belle animation après :) :)
