//pokemon areana project due december 4 2019
import java.util.*;
import java.io.*;//allows you to access files
//------------------------------------------------------------------------------------//
public class PokemonArena {//main class
 private static ArrayList<Pokemon>allPokes = new ArrayList<Pokemon>();//this conatins all the pokemons avalible
 private static ArrayList<Pokemon>myPokes = new ArrayList<Pokemon>();//this conatins the 4 pokemon that you choose
 private static ArrayList<Pokemon>enemyPokes = new ArrayList<Pokemon>();//all the pokemons hat arnt chosen are your enemies
 private static Pokemon currentPoke;
 private static Pokemon currentEnemyPoke;
 private static Scanner kb = new Scanner(System.in);

 public static void main (String[]args) throws IOException{//if it dosent it generates an exxception
  opening();
  load();//this prints out all the pokemon optiosn to you to choose from
  for(int i =0;i<4;i++){ //you need to chose 4 thats why its looped 4 times
    choose();
  }
  choosingYourBattlePokes();
  while(myPokes.size()>0 && enemyPokes.size()>0){//this makes the game continue untill the lists are empty 
  	battleSet();
  }
  if(myPokes.isEmpty()){//if myPokes list is empy it means you lost then calls the loser method
  	loser();
  }
  if(enemyPokes.isEmpty()){//if th eemeies list is empty that means you won
	 winnerWinnerChickenDinner();
  }
  kb.close();//closes all scanners
 } 
 public static void opening(){
 	System.out.println("Welcome Trainer, to Pokemon!!...\nIn order to defeat the enemypokes there are some important things you must know:\n");
 	System.out.println("HP:the amount of damage a poke can take before being KO’ed \nENERGY: is used to pay for attacks");
 	System.out.println("PASS:If a Pokemon does not have enough energy to perform an attack then it may have to do nothing for a turn while its energy recharges");
 	System.out.println("RETREAT:The Pokemon is replaced with one of your remaining Pokemon. The new Pokemon can not perform an action this turn");
 	System.out.println("ATTACKS:Each Pokemon has one or more attack, it looks like: ATTACK NAME, ENERGY, DAMAGE, SPECIAL.\n\nSPECIAL ATTACKS:There are five special type of attacks.");
 	System.out.println("	STUN:on top of normal damage there is a 50% change that the opponent will be stunned for one turn.");
 	System.out.println("	WILD CARD:The attack only has a 50% chance of success.");
 	System.out.println("	WILD STORM:Base attack has a 50% chance of success, no damage on a miss,if it succeeds then the Pokemon does a free wild storm attack");
 	System.out.println("	DISABLE:The target Pokemon becomes disabled, and its attacks will do 10 less damage for the rest of the battle");
 	System.out.println("	RECHARGE:Adds 20 energy to the attacking Pokemon");
 	System.out.println("In order to become Trainer Supreme you must defeat ALL the enemy pokes\nAre You Ready??\nGOOD LUCK!\n\n");
 }
 		
 public static void load()throws IOException{
   Scanner inFile = new Scanner(new BufferedReader(new FileReader("pokemon.txt"))); //this reads from the pokemon file, where all the pokeon data is held
   int n = Integer.parseInt(inFile.nextLine()); //gets the next interger
   for(int i = 0; i<n;i++){
     String line = inFile.nextLine();//this gets the pokemon name
     Pokemon poke = new Pokemon(line); //that name is now set as a pokemon
     allPokes.add(poke);//adds it to the array
     enemyPokes.add(poke); //also adds it to the enemy list 
   }
     for(int i =1; i<allPokes.size()+1;i++){//this prints out all the pokemon options which numbers infront of them
        System.out.println(i+"."+allPokes.get(i-1));
        inFile.close();//closes the file
     }
 }
 
 public static void choose(){ //this meathod prints out all avlaible pokes and asks you to choose your 4 battle pokes
   int num = 0;
   System.out.println("Choose Your Pokemons!");
   num = getInt();
   if(num >0 && num<=allPokes.size() && !myPokes.contains(allPokes.get(num-1))){//this makes sure there are no duplicate pokemon added onto the myPokes list
     myPokes.add(allPokes.get(num-1));//adds the chosen pokemon onto the myPokes list
     enemyPokes.remove(num-1);//this removes the pokemon that was chosen from the enemy list
     currentPoke = allPokes.get(num-1);
   }
   else{
     System.out.println("Not Vaild Pokemon...");//if the number isnt between 1-28, or its a duplicate the pokemon isnt vaild
     choose(); //then you chose again since it isnt valid
     }
 }
 
 public static void choosingYourBattlePokes(){//this is where out of your 4 pokes you choose your first battle poke
 	System.out.println("Choose Your Pokemon for Battle!"); //this is where you choose your pokemon
 	for(int i =1;i<myPokes.size()+1;i++){
     System.out.println(i+"."+myPokes.get(i-1));//this just prints it out in a list for easy choosing
    }
    int num = 0;  
    num = getInt();//gets the value you typed in
    System.out.println(" \n ");
   	if(num >0 && num<=myPokes.size()){//this makes sure there are no duplicate pokemon added onto the myPokes list
		System.out.println((myPokes.get(num-1))+", I Choose You!"); //saying i choose you ______!
	    currentPoke = myPokes.get(num-1); //gets the pokemon from your mypokes array list
	   }
	else{
    	System.out.println("Not Vaild Pokemon...");//if the number isnt between 1-28, or its a duplicate the pokemon isnt vaild
	    battleSet();//i
	    }
 }
 
 public static void battleSet(){//this sets up the battle, choosing your pokemon out of your chosen 4, telling you who your battling, and whihc player goes first
   Random randEnemy = new Random();//gets the random enemy
   currentEnemyPoke = enemyPokes.get(randEnemy.nextInt(enemyPokes.size()));
   System.out.println("Your Battle is Against: "+currentEnemyPoke+"! \n");
   Random randNum = new Random();//this gets to decide who goes first
   final int AI=2,PLAYER=1;
   int selector = (randNum.nextInt(2) + 1); //gets a random number either 1 or 2
   int turnCount = 0;
   while(myPokes.size()>0){//if the poke is alive and i have at least one poke in my roster///currentEnemyPoke.isAlive() && 
   
	   if(selector == PLAYER){ //meaning the player gets to go first               
	     myPokeAttacker(currentPoke,currentEnemyPoke);
	   }
	   else{
	     enemyPokeAttacker(currentEnemyPoke,currentPoke);//if the selector chosing anything but 1 (only options are 1 or 2) the enemy goes first 
	   }
	   turnCount+=1; // after each poke goes, the turn counter is added to, after evry other turn or round, energy gets added to the pokes
	   if(turnCount%2==0){ //thi sis where the energy gets added 
	   	System.out.println("****"+currentPoke);
	   	giveThemBackTheirEnergy();//this is the method that adds the energy 
	   	System.out.println("****"+currentPoke);
	   	System.out.println(currentPoke);
      	System.out.println(currentEnemyPoke);//prints out the pokes new stats 
      	System.out.println("\n");
	   	System.out.println("energy has been added");
	   }
	   selector = PLAYER + AI - selector;//instead of magic numbers using string to find out what poke goes next, 
   }									 //what ever the selector is gets rid of either the player or the AI leaving the opposite pokes to its turn
 }
 
 public static void giveThemBackTheirEnergy(){ //after each round 10 energy gets added
 	if (currentEnemyPoke.getEnergy() <= 40){
 		int currentEnemyPokeEn = currentEnemyPoke.getEnergy();//this is where i add my energy to my enemy pokes, they can max. have 50
 		currentEnemyPoke.setEnergy(currentEnemyPokeEn += 10);
 	}
 	else{
 		int currentEnemyPokeEn = currentEnemyPoke.getEnergy();
 		currentEnemyPoke.setEnergy(currentEnemyPokeEn += (50-(currentEnemyPokeEn))); //if they have over 40, then it gets them to 50 without going over
 	}
 	
 	for(int i=0;i<myPokes.size();i++){ //for all my pokes in my rotser they all get added 10 energy aftereach round
 		if((myPokes.get(i)).getEnergy() <= 40){
 			int retiredPokeEn = (myPokes.get(i)).getEnergy();
 			(myPokes.get(i)).setEnergy(retiredPokeEn+=10);
 		}
 		else{
 			int retiredPokeEn = (myPokes.get(i)).getEnergy();
 			(myPokes.get(i)).setEnergy(retiredPokeEn += (50-(retiredPokeEn))); //if they have over 40, then it gets them to 50 without going over
 		}
 	}
 }
 	
 public static void retreaterEnergy(){ //after a poke is defeated 20 hp gets added to all my pokes 
 	System.out.println("pokes HP has been replenished");
 	for(int i = 0; i<myPokes.size();i++){
 		if((myPokes.get(i)).getEnergy() <= (myPokes.get(i)).getMaxHp()){//this is getting that pokes hp and making sure it can only go up to thier max hp
 			int pokeHp = (myPokes.get(i)).getHp();
 			(myPokes.get(i)).setHp(pokeHp+=20);	//this sets thier new hp 
 			if((myPokes.get(i)).getHp() > (myPokes.get(i)).getMaxHp()){
 				(myPokes.get(i)).setHp((myPokes.get(i)).getMaxHp());
 			}
 		}
 	}
 }
 
 public static void winnerWinnerChickenDinner(){//if all enemey pokes are dead they win
 	System.out.println("You Have Succesfully Defeated All Enemy Pokemons...");
 	System.out.println("You Are Now Crowned Trainer Supreme");
 	System.out.println("Congrats Supreme Trainer ");
 }
 
 public static void loser(){//they lose if all your pokes have been defeated 
 	System.out.println("The Enemy "+currentEnemyPoke+" Has Defeated You :(");
 	System.out.println("Would You Like To Play Again? (y/n)");
 	String ans = kb.nextLine();
 	if(ans.equalsIgnoreCase("y")){ //asks them if they want to play gaain
      choosingYourBattlePokes();
     }
     else{ 
          System.out.println("Thanks For Playing Trainer");//if they dont the screen automatically exits 
          System.exit(0);
 	}
 }
 
 public static int getInt(){ //just gets the ans, making it easier so i dont have to type it in everytime
 	return Integer.parseInt(kb.nextLine());
 }
 
 public static void myPokeAttacker(Pokemon pokes, Pokemon enemyPoke){//this is where your pokemon attacks \
    System.out.println(currentPoke);
    System.out.println(currentEnemyPoke);//prints out the pokes new stats
    System.out.println("\n"); 
 	if(currentPoke.getStun()==false){
		if(currentPoke.getHp() >=1){//aslong as their hp isnt lower than 1 thier still alive
		    System.out.println("Your Turn: \n What Do You Want to Do?");
		    String[] attacks = {"Attack","Pass","Retreat"};//all possible moves
		    for(int i = 1;i<4;i++){
		      System.out.println(i+". "+attacks[i-1]);//just prints out the possible moves
		    }
		    int num = getInt(); 
		     if(num == 1){ //if they choose attack 
		     	ArrayList<Attack> chooseAttack = currentPoke.getValidAttack();//gets all the pokemons possible attacks (typically 1 or 2)//in pokemon class
		     	if(chooseAttack.isEmpty()){//if their are no valid attacks it states they dont have enough energy
		     		System.out.println("You Don't have enought energy");
		     	}
		  		for(int i = 1; i<= chooseAttack.size(); i++){//if there any attacks it prints out those attacks and allows the user to choose whihc one
		       		System.out.println(i+"."+chooseAttack.get(i-1));
		       	}
		   		System.out.println("choose your attack: ");
		   		int attackNumber = getInt(); //gets the attack they want
		   		currentPoke.attack(currentEnemyPoke,attackNumber-1);//this is where the actual attack is
		     }
		     if(num == 2){//if they choose 2 they pass
		     	System.out.println("Are You Sure You Want To Pass?");
		       	String ans = kb.nextLine();//just asks them if thier sure about passing 
		       	if(ans.equalsIgnoreCase("y")){ 
			   		System.out.println("Pass");
			      	}
			    else{
			    	myPokeAttacker(currentPoke, currentEnemyPoke);//if they dont want to pass theier just brought back to possible actions 
			    }
		     }
		     if(num == 3){//if they chose 3, they retreat
		     	System.out.println("Are You Sure You Want To retreat? (y/n)");
		        String ans = kb.nextLine();
		        if(ans.equalsIgnoreCase("y")){ //asks them if they want to reatreat 
		      		System.out.println("retreat");
		      		choosingYourBattlePokes();//if they do want to retreat then thier brought back to thier possible poke options 
			    }
			    else{
			    	myPokeAttacker(currentPoke, currentEnemyPoke);//if they dont want to retreat theier just brought back to possible actions 
			    }
	 	 	}
		 	}
		 	else{
		 		System.out.println(currentPoke+" has died :(... choose new poke:");//if thier hp isnt greater than 1 they die
		 		myPokes.remove(currentPoke);//once they die then thier removed from the myPokes list
		 		//retreaterEnergy(); //energy is added to remianing pokes
		 		choosingYourBattlePokes();//the you choose your new poke
	 		}
		}
	 else{
		System.out.println("you miss your turn due to being stunned :(");
		currentPoke.setStun(false);	
		 }
 }
		 
 public static void enemyPokeAttacker(Pokemon enemyPoke, Pokemon pokes){ //a method 
 		if(currentEnemyPoke.getStun()==false){
		  	 if(currentEnemyPoke.getHp() >=1){//if thier hp is greater or = to 1 they can attack
		  	 	System.out.println("\n");//just for spacing
			 	System.out.println("Enemies Turn");
		     	ArrayList<Attack> chooseAttack = currentEnemyPoke.getValidAttack();//this gets the enemys valid attacks
		  		if(chooseAttack.isEmpty()){//if they have no valid attack then they automatically pass
		  			System.out.println(currentEnemyPoke+" has passed");
		  		}
		  		else{
		  			Random randNum = new Random();//otherwise it randomizes the enemys attacks
					int attackNumber = randNum.nextInt(chooseAttack.size());//gets the random attack
					currentEnemyPoke.attack(currentPoke,attackNumber); //sets the actual attack
					System.out.println("\n");
		  		}
		  		}
		     else{
		    	System.out.println(currentEnemyPoke+" has died\n");//if thier hp is 0 or lower than they have died 
				enemyPokes.remove(currentEnemyPoke);//they get removed from the list 
		    	retreaterEnergy();
		    	battleSet();
		    	//then energy is added
		    	
	      	}
	     }
	     else{
	     	System.out.println("enemies turn has been passed due to stun");
	 		currentEnemyPoke.setStun(false);	
	     }
	}
}	
//-------------------------------------------------------------------------------------// 
class Pokemon{ //poke class 
  private String name,type,resistance,weakness;
  private int hp, numAtt; 
  private ArrayList<Attack>allAttacks = new ArrayList<Attack>(); 
  private int disableAffect = 0;
  private int damageMult = 1;
  private int maxHp;
  private boolean disable, stunned,wildstormz; 
  public Pokemon(String line){  
  	String attackName, attackSpecial; //all varibles that get the pomemons information 
    int attackEnergy, attackDamage;
    String[] stats = line.split(",");//this spilts the poekmons stats 
    maxHp = Integer.parseInt(stats[1]);//this is the maximun amount of hp the pokecan hav 
    hp = Integer.parseInt(stats[1]);//this gets the pokes hp 
    numAtt = Integer.parseInt(stats[5]);//this gets the number of attacks the pokes have
    name = stats[0];//gets the pokes name
    type = stats[2];//thier type
    resistance = stats[3]; //their resistance
    weakness = stats[4];//thier weakness
    disable = false;
    stunned = false;
    wildstormz = false;
    for(int i = 0; i < numAtt; i++){//if thiers multiple attacks this one gets them al
      attackName = stats[6+i*4]; 
      attackEnergy = Integer.parseInt(stats[7+i*4]); 
      attackDamage = Integer.parseInt(stats[8+i*4]);
      attackSpecial = stats[9+i*4];
      allAttacks.add(new Attack(attackName,attackEnergy,attackDamage,attackSpecial));
    }
  }  
  int energy = 50;//sets the pokes energy to 50;
  
  public boolean isAlive(){
  	return hp > 1;
  }//checks to see if the poke is alive
  
  public int getMaxHp(){ //gets the max hp of each poke
  	return maxHp;
  }
  public void setMaxHp(int mh){//allows you to set it
  	maxHp = mh;
  }
  
  public int getHp(){//gets the pokes hp
    return hp;
  }
  public void setHp(int h){//allows you to set the pokes hp
    hp = h;
  }
  
  public int getEnergy(){ //allows you to get the pokes energy
    return energy;
  }
  public void setEnergy(int e){//allows you to set the pokes energy
    energy = e;
  }
  
  public boolean getStun(){
  	return stunned;
  } 
  public void setStun(boolean s){
  	stunned = s;
  }
    
  public void attack(Pokemon target, int n){//this is where the actual attack happens
  	disable = false;
    stunned = false;
    wildstormz = false;
  	double playerDamageMult = 1;//this multipler allows me to do attack hits/misses
    Attack att = allAttacks.get(n); //this gets the pokemons attack
    if((target.resistance).equals(type)){//if the pokes type matches the other pokes then the attacking pokes damage is cut in half
    	playerDamageMult = 0.5;
    }
    if((target.weakness).equals(type)){//if the pokes type matches the other poke, the damage is multiplied by 2 
    	playerDamageMult = 2;
    }
    
    if((att.special).equals("disable")){//if the pokes special is disable, the the boolean disable is true. 
  	  target.disable = true;
    }
  	if(disable == true){//if disable is true...
  		System.out.println(target+"has been disabled");
		if(att.damage >= 10){//and if the affected pokes attacks are greater than 10...
			disableAffect = 10;  //then it takes off 10 damage
 		}
  		else{
  			disableAffect = att.damage;//other wise it just takes the pokes attack amount off thier attack making it 0
  			}
  	}
    if((att.special).equals("wild storm")){//if special attack is wildstrom it callls the method 
    	target.wildstormz = true;
    }
    if (wildstormz == true){
		Random randNum = new Random();
		int attackNumber = randNum.nextInt(1)+1;
		if(attackNumber ==1 && target.hp > 1){
			System.out.println("attack hit");
			damageMult *= 1;
		}
		else{
			System.out.println("Attack Missed");
			damageMult *= 0;
			wildstormz = false;
		}	
 	}
 	if((att.special).equals("Recharge")){//if the special is recharge...
  		if(energy <= 30){// if thier energy is less than 30 than the full 20 energy is added
  			energy += 20;
  			}
  		else{
  			energy += (50-energy);//if their energy is greater than than 30 than it gets the amount needed until it turns to 50
  		}
  	}
  	if((att.special).equals("wild card")){//if the special is wildcard it calls the method wildcard
   		wildcard();
    }
  	if((att.special).equals("stun")){//if the special is stun it calls the method stun
  		stun(target);
  	} 
  		
	target.hp -= (((att.damage-disableAffect)*playerDamageMult)*damageMult);
	energy -= att.cost;
	System.out.println(att);
  
  }
		
 public void wildcard(){//special attack, wildcard is a normal attack that has a 50% chance of hhitting 
  	Random randNum = new Random();//gets a random numver between 1 and 0 
	int attackNumber = randNum.nextInt(1);
	if (attackNumber ==1){ //if the randomizer gets 1 the the attack i shir 
		System.out.println("Attack hit");
		damageMult *= 1;// multiples the attack by one
	}
	else{
		System.out.println("Attack Missed");
		damageMult *= 0;//if the attack is multiplied by 1 then no damage is done
	}
 }
 
 public void stun(Pokemon target){//special ttack stun, if the poke is hit, the loose thier turn, automatically passing
  	Random randNum = new Random();//only hits 50% of the time, the randomizer allows me to do that 
	int attackNumber = randNum.nextInt(1)+1;//the ranomizer gets 0 or 1
	damageMult *= 1;//still does normal damage 
	if(attackNumber ==1){ //if the number is 1 that means the attack is hit making them sti+unned 
		target.stunned = true;// if stunned == trune then the getter and seter method activate
		System.out.println("stunned");
	}
	else{
		System.out.println("stun missed");//other wise the attack is missed
		stunned = false;
  }
 }

 public ArrayList<Attack> getValidAttack(){ 			// this gets a pokemons valid attack by checking 
  	ArrayList<Attack>valid = new ArrayList<Attack>();  	//thier energy levels and only giving the attacks the have anough for
  	for(Attack att :allAttacks){
	  	if(att.cost <= energy){//seeing if the poke has enough energy 
  			valid.add(att);
	  	} 
	}
	return valid; //this just returns the valid attacks, whihc later get printed 
  }
  
 public String toString(){
    return name+" Hp:"+hp+ " Energy:"+energy; //this displays the poekmons information
  }
}
//--------------------------------------------------------------------------------------------------//
class Attack{ //attack class
  public String name,special;
  public int cost,damage;
  public Attack(String n, int c, int d, String s){ //gets the attack info 
    name = n;
    cost = c;
    damage = d;
    special = s;
  }
 	
  public String toString(){
    return name+" Damage:"+damage+" Cost:"+cost+" Special:"+special; //this displays the pokeomons attack information
  }
}