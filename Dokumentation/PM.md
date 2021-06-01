# Titel

Marcus Nylund 2021-06-01

## Inledning

Syftet med Projektet var att "återskapa" och lägga till saker till ATARIs spel Breakout. Jag fick ungefär 1 månad till arbetet
och jobbade på lektionstid och lite på min fritid. Jag använde Mycket av koden Magnus silverdal hade get mig och TE19 under grafik momentet
 för att bygga up mit projekt ifrån.

## Bakgrund

Redovisa arbetets olika delar. Så att läsaren förstår vad du gjort och hur.
###Poängen av spelet

Poängen i spelet är att spelaren ska använda en platform som dom kan styra för att studsa en boll så att den förstör alla "Bricks" genom att träffa dom,
spelet slutar bara om spelaren tappar bollen för många gånger och det handlar om att få så många poäng innan det händer.

###Allas ATARIs Breakout delar

Platform är det spelaren styr och kan bara gå vänster och höger, beroende på platformens hastighet och bollens hastighet när dom träffar varandra händer olika saker
och är sättet spelaren kan "försöka" få bollen att träffa vart hen vill.

Bollarna i spelet studsar nästan på allting, väggar, taket, "Bricks" och spelarens platform. bollarna **förstör** "Bricks" om dom träffar dom och är hur du får poäng.
spelaren förlorar om hen tappar alla bollar förmånga gånger.  vid varje ny map eller vid början av spelet börjar användaren genom att "skuta" bollen ifrån deras platform.

"Bricks" läggs ut enligt maps och dom får en "random" färg när dom skapas. när alla "Bricks" är borta slumpas en ny map och ny bricks läggs ut.

###Jag har lagt till

"Powerups" skapas när "Bricks" förstörs och kan vara bra eller dålig för spelaren, det som kan hända är att platformen kan bli större/mindre eller snabbare/långsammare, spelaren kan också få mer poäng eller mer bollar.
kraften visas med olika färger och spelet blir svårare och enklare eftersom spelaren måste hålla reda på mer saker men om spelaren får bra "powerups" kommer spelet bli enklare tills spelaren försör alla bricks, efter det blir alla värderna som "platformsize" normala igen.

"Scaling". spelet blir svårare destå mer du kör eftersom bollarna blir snabbare och snabbare.

***Vet inte hur jag använder bilder men tänk dig en screenshot av spelet med pilar som pekar på olika delar och namn på pilarna som beskriver vad dom pekar på***

## Positiva erfarenheter

Det har gått bra att jobba med mina "Dataklasser". jag har användt en dataklass för minna bollar, powerups och mina bricks för att hålla reda på deras position, hitbox, hastighet, färg och kraft.

Det har också gått bra att fortsätta jobba med Canvas och att jobba utan bilder, jag har skapat allt i mitt spel med Canvas.

Det har gått bra att använda mina kunskaper ifrån NoTE19pad för att läsa in från filer och med hjälp från Adam sortera värden i mitt program mot det i filen. 
 
## Negativa erfarenheter

Det har inte gått väldigt bra att kunna kolla ifrån vilket håll min boll koliderar mot en "Brick" och vilket hål det borde studsta,
jag använder just nu någonting som inte alls är effektivt eftersom jag först rör min boll i X-axeln sedan kollar jag kollison och sedan rör jag Y-axeln och sedan kollar jag kolliso igen.
 För mina framtida projekt borde jag tänka mer runt detta problem för att kunna lösa detta och andra liknande problem

## Sammanfattning

Jag har lärt mig mer om "Dataklasser" och om filhantering, jag har skapat ett spel som fungerar helt okej och som de flesta användarna förstår när dom använder det för den första gången.
Spelet ser okej ut även om alla delar inte är perfekta.
om jag skulle forstsätta jobba på spelet skulle jag fixa kollsionen mellan bollarna och "Brics" och jag skulle fixa fler "powerups" samt ge dom alla korisponderande symboler på dem.
Jag skulle också lägga till ljud till spelet som backgrunds ljud, ball kollsion ljud och mer. 
