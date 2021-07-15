package unsw.loopmania;

public class RewardGenerator implements Generator{
    
    //use if else here, could change afterwards
    @Override
    public void generate(Enemy enemy) {
        if(enemy instanceof Slug) {
            loadGold((new Random()).nextInt(40) + 10 + (this.world.getCycle().get() * 10));
            loadExp(20 + 100 * this.world.getCycle().get());
        } else if (enemy instanceof Vampire) {
            loadGold((new Random()).nextInt(1300) + 200 + (this.world.getCycle().get() * 20));
            loadExp(500 + 500 * this.world.getCycle().get());
            Random random = new Random();
            int choice = random.nextInt(100);
            if (choice < 10) {
                loadSword();
            }
            else if (choice < 20) {
                loadArmour();
            }
            else if (choice < 30) {
                loadShield();
            }
            else if (choice < 40) {
                loadHelmet();
            }
            else if (choice < 50) {
                loadPotion();
            }
            else if (choice < 60) {
                loadCard();
            }
        } else if (enemy instanceof Zombie) {
            loadGold(new Random().nextInt(100)+100+15*world.getCycle().get());
            loadExp(200+150*world.getCycle().get());
    
            Random random = new Random();
            int choice = random.nextInt(100);
            if (choice < 5) {
                loadSword();
            }
            else if (choice < 8) {
                loadArmour();
            }
            else if (choice < 10) {
                loadCard();
            }
            else if (choice < 15) {
                loadPotion();
            }
        }
    
        
    }
    public void loadCard() {
        int cardChoice = new Random().nextInt(7);
        if (cardChoice == 0) {
            loadVampireCard();
        }
        else if (cardChoice == 1) {
            loadZombieCard();    
        }
        else if (cardChoice == 2) {
            loadVillageCard();    
        }
        else if (cardChoice == 3) {
            loadBarrackCard();    
        }
        else if (cardChoice == 4) {
            loadTowerCard();    
        }
        else if (cardChoice == 5) {
            loadTrapCard();    
        }
        else if (cardChoice == 6) {
            loadCampFireCard();    
        }
    }
}
