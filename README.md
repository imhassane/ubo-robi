# ROBI

### Principe
Le but est de d�placer Robi qui est un personnage (represent� par un quadrilat�re) � l'aide de commandes.
<br />
Les commandes ressemblent au langage LISP
Un parseur est charg� de recup�rer les commandes entr�es par l'utilisateur et les renvoie sous forme d'expr�ssions.<br/>

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
Ce script va cr�er un fond noir, un robi blanc (waw, super) <br />
Puis va le d�placer en faisant une translation de ses coordonn�es. On fait une pause de 100 ms puis on le d�place encore.
<br />
Ce qui fait une tr�s belle animation apr�s :) :)
