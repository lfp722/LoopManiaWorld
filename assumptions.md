Assumptions: 


Tool Bar Limits:

1. Only one helmet, one armor, one sword and one shield can be equipped.
2. 32 (4*8) unequipped inventory(maximum hold items)


Enemy value (c <= cycle num):

Slug:

1. Health = 3*c
2. Damage = 2*c^(2/3)
3. Battle radius = Support radius = 3
4. Possible reward: 10-50+10*c gold, exp = 20+100*c
5. Speed = 1 / s


Zombie:

1. Health = 10*c
2. Damage = 10*c+1
3. Battle radius = 3
   Support radius = 5
4. Chance to critical bite = c * 10%(maximum 0.9)
5. Possible reward: 100-200 gold, kinds of sword, kinds of armor, some cards; exp = 200+150*c
6. Speed = 0.75 / s


Vampire:

1. Health = 3*c^1.5+50
2. Damage = c^2+5*c
3. Battle radius = 4
4. Support radius = 6
5. Chance to critical bite = c * 10%(maximum 0.9), critical damage 10%-40% 
6. Possible reward: 200-1500 gold, anything but with different chances, exp = 500+500*c
7. Speed = 1.5 / s


Weapons Value(Lv represents weapon level):

Sword: Damage = Lv + 2
Stake: Damage = 0.9*Lv + 1.5, (3*Lv+5 when hits Vampires)
Staff: Damage = 0.85*Lv + 1, chance to trance = Lv * 15%


Outfits Value(Lv represents outfit level):

Armor: Defence = 3 + Lv*10
Shield: Defence = 3 + Lv*10
Helmet: Defence = 3 + Lv*10


Other Items:

Health Potion: recover 20% of maximum health


Rare Items:

The One Ring: 0.1% chance to get when finish one battle


Assumption for prices:

Sword: price = 250, can enhance level by paying (100*Lv^2-150) 
Stake: price = 250, can enhance level by paying (100*Lv^2-150) 
Staff: price = 400, can enhance level by paying (200*Lv^2-200)
Armor: price = 250, can enhance level by paying (100*Lv^2-150) 
Shield: price = 250, can enhance level by paying (100*Lv^2-150) 
Helmet: price = 250, can enhance level by paying (100*Lv^2-150)
Health Potion: price = 100


Basic Attributes for Character(Lv for Character level):

Attack = 3 + 2*Lv 
Defence = 0
Health = 15 + 20 * Lv 
Exp = 0
Gold = 0


Experience Level:

Exp to next level = 3000*Lv^2 - 200


Assumptions for Buildings(c = cycle num):

Vampire castle will produce 0-3 vampires randomly(25% for each case).
Zombie Pit will produce 0-3 zombies randomly.
The rooting radius of Tower is 2, damage = 10 
Village will recover 20 fixed-amount health. 
Barracks will produce 1 allied soldier when passing by. 
Trap will cause a 4-value damage.
The battle radius of Campfire is 2.


Assumption for Fighting System:

Character will attack first.
Allied soldiers will suffer from attacking first, and Character will suffer only when there is no allied soldier.
The enemy will attack character within its support range.
Enemies in battle will stop moving until battle ends.
Enemies moves in a range of fields around its birth point when free of battle.
Character and enemies can attack each other regardless of distance when battle starts. 
Character will attack the nearest enemy first.
The character with his soldiers and enemies will attack each other turn by turn. 
Vampire can detect campfire of the radius of 2.
Soldiers’ attributes will be 20% of character’s.


Assumptions for UI:

Select specific mode by click related button.
Click Pause or Resume to pause or resume game.
Drag the card to a specific tile to set up building.
Click return to main menu button to re-select mode and start.
Player can sell, equip and unequip items in bags by clicking related button. 
Press “H” to take Health Potion.


Assumption for Items Appearance:

Only gold will appear when slugs are defeated.
When zombie is killed, there must be gold, different chances for additional items(weapons, potions, outfits, card).
Card and potion may appear on path tiles by a chance < 2%.
When vampire is killed, there must be gold with one additional reward(any item). But chances for another additional item.



Extra Assumptions For M2:
1. Time for Staff trance is a whole battle time.
2. Press H to use Potion.
3. Character may have maximum 4 soldiers, when there are new incomers, we remove the oldest incomer, and add the new incomer.
4. The one ring only appears after defeating a vampire.
5. The character can only hold at most one TheOneRing at the same time.
6. Stake and Staff can only be bought.
7. The one ring can only be obtained from battle with Vampire, the chance is 1/1000.
8. Price for potion become 1000.
9. Each type of card would appear in the same possibility.
10. Store can only upgrade things that are already equipped.
11. when click "Exit to main menu", we will be back to main menu with two buttons "resume" and "quit".
12. Once the one ring is obtained, it will be automatically generated.
13. Zombie can only transfer soldiers into Zombie. If it critical bites on character or tranced enemies, more damage will be caused.
14. If card are dragged the wrong area, it will be destroyed.