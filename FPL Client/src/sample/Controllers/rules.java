package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;



public class rules {

    @FXML
    Text text;
    @FXML
    Button btnEp1, btnEp11, btnEp12,btnEp13,btnEp14,btnEp15,btnEp16,btnEp17;
    @FXML
    VBox VB0, VB;

    String iniSquads = "Squad Size\n" +
            "To join the game select a fantasy football squad of 15 players, consisting of:\n" +
            "\n" +
            "2 Goalkeepers\n" +
            "5 Defenders\n" +
            "5 Midfielders\n" +
            "3 Forwards\n" +
            "Budget\n" +
            "The total value of your initial squad must not exceed £100 million.\n" +
            "\n" +
            "Players Per Team\n" +
            "You can select up to 3 players from a single Premier League team.";

    String manageSquad = "Choosing your starting 11\n" +
            "From your 15 player squad, select 11 players by the Gameweek deadline to form your team.\n" +
            "\n" +
            "All your points for the Gameweek will be scored by these 11 players, however if one \n" +
            "or more doesn't play they may be automatically substituted.\n" +
            "\n" +
            "Your team can play in any formation providing that 1 goalkeeper, at least 3 defenders \n" +
            "and at least 1 forward are selected at all times.\n" +
            "\n" +
            "Selecting a Captain and a Vice-Captain\n" +
            "From your starting 11 you nominate a captain and a vice-captain. Your captain's score \n" +
            "will be doubled.\n" +
            "\n" +
            "If your captain plays 0 minutes in the Gameweek, the captain will be changed to \n" +
            "the vice-captain.\n" +
            "\n" +
            "If both captain and vice-captain play 0 minutes in a Gameweek, then no player's \n" +
            "score will be doubled.\n" +
            "\n" +
            "Prioritising Your Bench For Automatic Substitutions\n" +
            "Your substitutes provide cover for unforeseen events like injuries and postponements \n" +
            "by automatically replacing starting players who don't play in a Gameweek.\n" +
            "\n" +
            "Playing in a Gameweek means playing at least 1 minute or receiving a yellow / red card.\n" +
            "\n" +
            "Based on the priorities you assign, automatic substitutions are processed at the end \n" +
            "of the Gameweek as follows:\n" +
            "\n" +
            "If your Goalkeeper doesn't play in the Gameweek, he will be substituted by your \n" +
            "replacement Goalkeeper, if he played in the Gameweek.\n" +
            "If any of your outfield players don't play in the Gameweek, they will be substituted \n" +
            "by the highest priority outfield substitute who played in the Gameweek and doesn't \n" +
            "break the formation rules (eg. If your starting team has 3 defenders, a defender \n" +
            "can only be replaced by another defender).";

    String transfers = "After selecting your squad you can buy and sell players in the transfer market. \n" +
            "Unlimited transfers can be made at no cost until your first deadline.\n" +
            "\n" +
            "After your first deadline you will receive 1 free transfer each Gameweek. \n" +
            "Each additional transfer you make in the same Gameweek will deduct 4 points \n" +
            "from your total score (Classic scoring) and match score (Head-to-Head scoring) \n" +
            "at the start of the next Gameweek.\n" +
            "\n" +
            "If you do not use your free transfer, you are able to make an additional free \n" +
            "transfer the following Gameweek. If you do not use this saved free transfer in \n" +
            "the following Gameweek, it will be carried over until you do. You can never \n" +
            "have more than 1 saved transfer.\n" +
            "\n" +
            "Wildcards\n" +
            "For information on wildcards please refer to the chips section of the rules.\n" +
            "\n" +
            "Player Prices\n" +
            "Player prices change during the season dependent on the popularity of the \n" +
            "player in the transfer market. Player prices do not change until the season \n" +
            "starts.\n" +
            "\n" +
            "The price shown on your transfers page is a player's selling price. This \n" +
            "selling price may be less than the player's current purchase price as a \n" +
            "sell-on fee of 50% (rounded up to the nearest £0.1m) will be applied on \n" +
            "any profits made on that player.";

    String chips = "Chips can be used to potentially enhance your team's performance during the season.\n" +
            "\n" +
            "Only one chip can be played in a single Gameweek. The chips available are as follows:\n" +
            "\n" +
            "Name\t\tEffect\n" +
            "Bench Boost\tThe points scored by your bench players in the next Gameweek are \n" +
            "\t\tincluded in your total.\n" +
            "\n" +
            "Free Hit\tMake unlimited free transfers for a single Gameweek. At the next \n" +
            "\t\tdeadline your squad is returned to how it was at the start \n" +
            "\t\tof the Gameweek.\n" +
            "\n" +
            "Triple Captain\tYour captain points are tripled instead of doubled in the next \n" +
            "\t\tGameweek.\n" +
            "\n" +
            "Wildcard\tAll transfers (including those already made) in the Gameweek are \n" +
            "\t\tfree of charge.\n" +
            "\n" +
            "The Bench Boost and Triple Captain chips can each be used once a season and are \n" +
            "played when saving your team on the my team page. They can be cancelled at \n" +
            "anytime before the Gameweek deadline.\n" +
            "\n" +
            "The Free Hit chip can be used once a season, is played when confirming \n" +
            "your transfers and can't be cancelled after confirmed.\n" +
            "\n" +
            "The Wildcard chip can be used twice a season. The first wildcard will be \n" +
            "available from the start of the season until Sat 28 Dec 17:30. The second \n" +
            "wildcard will be available after this date in readiness for the January \n" +
            "transfer window opening and remain available until the end of the season. \n" +
            "The Wildcard chip is played when confirming transfers that cost points and \n" +
            "can't be cancelled once played.\n" +
            "\n" +
            "Please note that when playing either a Wildcard or your Free Hit chip, \n" +
            "any saved free transfers will be lost. You will be back to the usual \n" +
            "1 free transfer the following Gameweek.";
    String deadlines = "All changes to your team (starting 11, transfers, captain \n" +
            "changes, substitiution priorities) must \n" +
            "be made by the Gameweek deadline in order to take effect for that set \n" +
            "of matches.\n" +
            "\n" +
            "Deadlines are subject to change and will be 1 hour before the kick-off \n" +
            "time in the first match of the Gameweek.\n" +
            "\n" +
            "Gameweek\tDeadline\n" +
            "Gameweek 1\tSat 10 Aug 00:00\n" +
            "Gameweek 2\tSat 17 Aug 16:30\n" +
            "Gameweek 3\tSat 24 Aug 00:00\n" +
            "Gameweek 4\tSat 31 Aug 16:30\n" +
            "Gameweek 5\tSat 14 Sep 16:30\n" +
            "Gameweek 6\tSat 21 Sep 00:00\n" +
            "Gameweek 7\tSat 28 Sep 16:30\n" +
            "Gameweek 8\tSat 5 Oct 16:30\n" +
            "Gameweek 9\tSat 19 Oct 16:30\n" +
            "Gameweek 10\tSat 26 Oct 00:00\n" +
            "Gameweek 11\tSat 2 Nov 17:30\n" +
            "Gameweek 12\tSat 9 Nov 01:00\n" +
            "Gameweek 13\tSat 23 Nov 17:30\n" +
            "Gameweek 14\tSat 30 Nov 17:30\n" +
            "Gameweek 15\tWed 4 Dec 00:30\n" +
            "Gameweek 16\tSat 7 Dec 17:30\n" +
            "Gameweek 17\tSat 14 Dec 17:30\n" +
            "Gameweek 18\tSat 21 Dec 17:30\n" +
            "Gameweek 19\tThu 26 Dec 17:30\n" +
            "Gameweek 20\tSat 28 Dec 17:30\n" +
            "Gameweek 21\tWed 1 Jan 17:30\n" +
            "Gameweek 22\tSat 11 Jan 01:00\n" +
            "Gameweek 23\tSat 18 Jan 17:30\n" +
            "Gameweek 24\tWed 22 Jan 00:30\n" +
            "Gameweek 25\tSat 1 Feb 17:30\n" +
            "Gameweek 26\tSat 8 Feb 17:30\n" +
            "Gameweek 27\tSat 22 Feb 17:30\n" +
            "Gameweek 28\tSat 29 Feb 01:00\n" +
            "Gameweek 29\tSat 7 Mar 17:30\n" +
            "Gameweek 30\tSat 14 Mar 17:30\n" +
            "Gameweek 31\tSat 21 Mar 01:00\n" +
            "Gameweek 32\tSat 4 Apr 16:30\n" +
            "Gameweek 33\tSat 11 Apr 00:00\n" +
            "Gameweek 34\tSat 18 Apr 16:30\n" +
            "Gameweek 35\tSat 25 Apr 16:30\n" +
            "Gameweek 36\tSat 2 May 19:00\n" +
            "Gameweek 37\tSat 9 May 19:00\n" +
            "Gameweek 38\tSun 17 May 19:00";
    String scoring = "During the season, your fantasy football players will be allocated \n" +
            "points based on their performance \n" +
            "in the Premier League.\n" +
            "\n" +
            "Action\tPoints\n" +
            "For playing up to 60 minutes\t1\n" +
            "For playing 60 minutes or more (excluding stoppage time)\t2\n" +
            "For each goal scored by a goalkeeper or defender\t6\n" +
            "For each goal scored by a midfielder\t5\n" +
            "For each goal scored by a forward\t4\n" +
            "For each goal assist\t3\n" +
            "For a clean sheet by a goalkeeper or defender\t4\n" +
            "For a clean sheet by a midfielder\t1\n" +
            "For every 3 shot saves by a goalkeeper\t1\n" +
            "For each penalty save\t5\n" +
            "For each penalty miss\t-2\n" +
            "Bonus points for the best players in a match\t1-3\n" +
            "For every 2 goals conceded by a goalkeeper or defender\t-1\n" +
            "For each yellow card\t-1\n" +
            "For each red card\t-3\n" +
            "For each own goal\t-2\n" +
            "Clean sheets\n" +
            "A clean sheet is awarded for not conceding a goal whilst on the pitch and \n" +
            "playing at least 60 minutes " +
            "(excluding stoppage time).\n" +
            "\n" +
            "If a player has been substituted when a goal is conceded this will not \n" +
            "affect any clean sheet bonus.\n" +
            "\n" +
            "Red Cards\n" +
            "If a player receives a red card, they will continue to be penalised for \n" +
            "goals conceded by their team.\n" +
            "\n" +
            "Red card deductions include any points deducted for yellow cards.\n" +
            "\n" +
            "Assists\n" +
            "Assists are awarded to the player from the goal scoring team, who makes \n" +
            "the final pass before a goal is " +
            "scored. An assist is awarded whether the pass was intentional (that it \n" +
            "actually creates the chance) or " +
            "unintentional (that the player had to dribble the ball or an inadvertent \n" +
            "touch or shot created the chance).\n" +
            "\n" +
            "If an opposing player touches the ball after the final pass before a goal \n" +
            "is scored, significantly altering " +
            "the intended destination of the ball, then no assist is awarded. Should a \n" +
            "touch by an opposing player be " +
            "followed by a defensive error by another opposing outfield player then no \n" +
            "assist will be awarded. If the " +
            "goal scorer loses and then regains possession, then no assist is awarded.\n" +
            "\n" +
            "Rebounds\n" +
            "If a shot on goal is blocked by an opposition player, is saved by a \n" +
            "goalkeeper or hits the woodwork, and " +
            "a goal is scored from the rebound, then an assist is awarded.\n" +
            "\n" +
            "Own Goals\n" +
            "If a player shoots or passes the ball and forces an opposing player \n" +
            "to put the ball in his own net, then \n" +
            "an assist is awarded.\n" +
            "\n" +
            "Penalties and Free-Kicks\n" +
            "In the event of a penalty or free-kick, the player earning the penalty \n" +
            "or free-kick is awarded an assist \n" +
            "if a goal is directly scored, but not if he takes it himself, in which \n" +
            "case no assist is given.\n" +
            "\n" +
            "Finalising Assists\n" +
            "Assist points awarded by Opta within Fantasy Premier League are calculated \n" +
            "using additional stats which may " +
            "differ from other websites. For example, some other sites would not show \n" +
            "an assist where a player has won a " +
            "penalty.\n" +
            "\n" +
            "For the avoidance of doubt, points awarded in-game are subject to change \n" +
            "up until one hour after the final " +
            "whistle of the last match of any given day. Once the points have all been \n" +
            "updated on that day, no further \n" +
            "adjustments to points will be made.\n" +
            "\n" +
            "Bonus Points\n" +
            "The Bonus Points System (BPS) utilises a range of statistics to create \n" +
            "a BPS score for every player. The " +
            "three best performing players in each match will be awarded bonus points. \n" +
            "3 points will be awarded to the " +
            "highest scoring player, 2 to the second best and 1 to the third.\n" +
            "\n" +
            "Examples of how bonus point ties will be resolved are as follows:\n" +
            "\n" +
            "If there is a tie for first place, Players 1 & 2 will receive 3 points \n" +
            "each and Player 3 will receive 1 point.\n" +
            "If there is a tie for second place, Player 1 will receive 3 points and \n" +
            "Players 2 and 3 will receive 2 points each.\n" +
            "If there is a tie for third place, Player 1 will receive 3 points, \n" +
            "Player 2 will receive 2 points and Players \n" +
            "3 & 4 will receive 1 point each.\n" +
            "How is the BPS score calculated?\n" +
            "Players score BPS points based on the following statistics (one point \n" +
            "for each unless otherwise stated):\n" +
            "\n" +
            "Action\tBPS\n" +
            "Playing 1 to 60 minutes\t3\n" +
            "Playing over 60 minutes\t6\n" +
            "Goalkeepers and defenders scoring a goal\t12\n" +
            "Midfielders scoring a goal\t18\n" +
            "Forwards scoring a goal\t24\n" +
            "Assists\t9\n" +
            "Goalkeepers and defenders keeping a clean sheet\t12\n" +
            "Saving a penalty\t15\n" +
            "Save\t2\n" +
            "Successful open play cross\t1\n" +
            "Creating a big chance (a chance where the receiving player should score)\t3\n" +
            "For every 2 clearances, blocks and interceptions (total)\t1\n" +
            "For every 3 recoveries\t1\n" +
            "Key pass\t1\n" +
            "Successful tackle (net*)\t2\n" +
            "Successful dribble\t1\n" +
            "Scoring the goal that wins a match\t3\n" +
            "70 to 79% pass completion (at least 30 passes attempted)\t2\n" +
            "80 to 89% pass completion (at least 30 passes attempted)\t4\n" +
            "90%+ pass completion (at least 30 passes attempted)\t6\n" +
            "Conceding a penalty\t-3\n" +
            "Missing a penalty\t-6\n" +
            "Yellow card\t-3\n" +
            "Red card\t-9\n" +
            "Own goal\t-6\n" +
            "Missing a big chance\t-3\n" +
            "Making an error which leads to a goal\t-3\n" +
            "Making an error which leads to an attempt at goal\t-1\n" +
            "Being tackled\t-1\n" +
            "Conceding a foul\t-1\n" +
            "Being caught offside\t-1\n" +
            "Shot off target\t-1\n" +
            "*Net successful tackles is the total of all successful tackles minus any \n" +
            "unsuccessful tackles. Players " +
            "will not be awarded negative BPS points for this statistic.\n" +
            "\n" +
            "Data is supplied by Opta and once it has been marked as final will not be \n" +
            "changed. We will not enter into " +
            "discussion around any of the statistics used to calculate this score for \n" +
            "any individual match.";
    String leagues = "After entering your squad, you can join and create leagues to \n" +
            "compete with friends and other game \n" +
            "players.\n" +
            "\n" +
            "League Types\n" +
            "Private Leagues\n" +
            "Private leagues are the heart and soul of the game, where you compete against \n" +
            "your friends. Just create a " +
            "league and then send out the unique code to allow your friends to join, easy!\n" +
            "\n" +
            "You can compete in up to 20 private leagues. There's no limit on the number of \n" +
            "teams in a single league.\n" +
            "\n" +
            "Public Leagues\n" +
            "Need an extra challenge? Then join a public league of randomly assigned teams. \n" +
            "You can compete in up to 3 " +
            "public leagues.\n" +
            "\n" +
            "Global Leagues\n" +
            "You are automatically entered into the following global leagues:\n" +
            "\n" +
            "The overall league featuring all registered teams\n" +
            "A league for fellow managers from your country\n" +
            "A league for supporters of your favourite Premier League team\n" +
            "A league for managers starting the same Gameweek as you\n" +
            "The Fantasy Cup (starts in Gameweek 17)\n" +
            "League Scoring\n" +
            "All leagues score on either a Classic or Head-to-Head basis.\n" +
            "\n" +
            "Classic Scoring\n" +
            "In a league with classic scoring, teams are ranked based on their total points \n" +
            "in the game. You can join " +
            "or leave a league with classic scoring at any point during the season.\n" +
            "\n" +
            "In the event of a tie between teams, the team who has made the least amount of \n" +
            "transfers will be positioned " +
            "higher. Any transfers made using a wildcard or free hit will not count towards \n" +
            "total transfers made.\n" +
            "\n" +
            "Classic scoring leagues are run over a number of phases:\n" +
            "\n" +
            "Phase\tFirst Gameweek\tLast Gameweek\n" +
            "Overall\tGameweek 1\tGameweek 38\n" +
            "August\tGameweek 1\tGameweek 4\n" +
            "September\tGameweek 5\tGameweek 7\n" +
            "October\tGameweek 8\tGameweek 10\n" +
            "November\tGameweek 11\tGameweek 14\n" +
            "December\tGameweek 15\tGameweek 20\n" +
            "January\tGameweek 21\tGameweek 24\n" +
            "February\tGameweek 25\tGameweek 28\n" +
            "March\tGameweek 29\tGameweek 31\n" +
            "April\tGameweek 32\tGameweek 35\n" +
            "May\tGameweek 36\tGameweek 38\n" +
            "Any transfer point deductions in the Gameweek before a phase starts won't be \n" +
            "deducted from the phase score. " +
            "For example, any transfers made in Gameweek 4 (preparing for Gameweek 5) won't \n" +
            "be deducted from your September \n" +
            "score.\n" +
            "\n" +
            "Head-to-Head Scoring\n" +
            "In a league with Head-to-Head scoring, every team plays a match against another \n" +
            "team in the league each Gameweek. " +
            "The match result is based on the Gameweek score of each team minus any transfer \n" +
            "points spent preparing for the " +
            "" + "Gameweek.\n" +
            "\n" +
            "3 points are awarded for a win and 1 point for a draw, teams are then ranked on \n" +
            "points earned in Head-to-Head " +
            "matches.\n" +
            "\n" +
            "Head-to-Head fixtures are generated at the start of the league's first Gameweek. \n" +
            "Once these fixtures have been " +
            "generated the league is locked and teams will not be able to join or leave.\n" +
            "\n" +
            "If a Head-to-Head league has an odd number of teams then an average team will \n" +
            "join the league to ensure each " +
            "team has a fixture every Gameweek. This team will always score the Gameweek average.\n" +
            "\n" +
            "In the event of a tie between teams, the team who has most game points will be \n" +
            "positioned higher.\n" +
            "\n" +
            "Head-to-Head Knock-out stage\n" +
            "Optionally, a Head-to-Head league may end with a knock-out stage over up to 3 \n" +
            "Gameweeks with the league winner " +
            "decided by a match in the final Gameweek. The automatically entered average team \n" +
            "will not enter the knock-out stage, " +
            "their place being taken by the next placed team.\n" +
            "\n" +
            "In the event of a tie between teams in a Head-to-Head knock-out match, the \n" +
            "following tie-breaks will be used:\n" +
            "\n" +
            "Most goals scored in the Gameweek\n" +
            "Fewest goals conceded in the Gameweek\n" +
            "Virtual coin toss";
    String cup = "The first round of the cup will be Gameweek 17.\n" +
            "\n" +
            "Qualifying\n" +
            "The top 4,194,304 scorers in Gameweek 16 will enter the first round. If there \n" +
            "are more than 4,194,304 qualifiers " +
            "then there will be a random draw amongst the lowest scorers to see who qualifies.\n" +
            "\n" +
            "How it works\n" +
            "Each qualifying team will be randomly drawn against another in the first round. \b" +
            "The winner (the team with the " +
            "highest Gameweek score minus any transfer points), will progress to the second \b" +
            "round and another random draw, " +
            "the losers are out! This process continues until the final Gameweek when the two \b" +
            "remaining teams contest the " +
            "cup final.\n" +
            "\n" +
            "If a cup match is drawn, then the following tie-breaks will be applied until a \b" +
            "winner is found:\n" +
            "\n" +
            "Most goals scored in the Gameweek\n" +
            "Fewest goals conceded in the Gameweek\n" +
            "Virtual coin toss";

    Button[] buttons = new Button[8];
    String[] strings = new String[8];

    public void initialize(){
        strings[0] = iniSquads; strings[1] = manageSquad;strings[2] = transfers;strings[3] = chips;strings[4] = deadlines;
        strings[5] = scoring;strings[6] = leagues; strings[7]=cup;
        buttons[0] = btnEp1;buttons[1] = btnEp11;buttons[2] = btnEp12;buttons[3] = btnEp13;buttons[4] = btnEp14;
        buttons[5] = btnEp15;buttons[6] = btnEp16;buttons[7] = btnEp17;
        for(int i=0; i<=7; i++){
            buttons[i].setId("0");
        }
        VB0.setPrefHeight(580);
        VB.setOpacity(0);
    }

    @FXML
    private void expandSection(MouseEvent event){
        for(int i=0; i<=7; i++) {
            if (event.getSource().equals(buttons[i])){
                if(buttons[i].getId()=="0"){
                    VB.setOpacity(1);
                    text.setText(strings[i]);
                    VB.toBack();
                    for(int j=i; j>=0; j--){
                        buttons[j].toBack();
                    }
                    buttons[i].setId("1");
                    for(int k=0; k<=7; k++){
                        if(k!=i){
                            buttons[k].setId("0");
                        }
                    }
                }
                else {
                    VB.setOpacity(0);
                    VB.toFront();
                    buttons[i].setId("0");
                    text.setText("");
                }
            }

        }
    }
}
