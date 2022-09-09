package sample.Database;
import javafx.beans.property.IntegerProperty;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import sample.ControllerClasses.InterfaceController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.invoke.CallSite;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class DBController {
    private Connection connection;

    public void Connect() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Basketball", "postgres", "martynov");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void exportData(String path){
        Runtime rt = Runtime.getRuntime();
        Process p;
        ProcessBuilder pb;
        rt = Runtime.getRuntime();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateOnly = new SimpleDateFormat("MMddyyyy");
        String fileName=path+"\\backup"+dateOnly.format(cal.getTime())+".tar";
        pb = new ProcessBuilder(
                "C:\\Program Files\\PostgreSQL\\12\\bin\\pg_dump.exe",
                "--host", "localhost",
                "--port", "5432",
                "--username", "postgres",
                "--no-password",
                "--format", "t",
                "--blobs",
                "--verbose", "--file", fileName, "Basketball");
        try {
            final Map<String, String> env = pb.environment();
            env.put("PGPASSWORD", "martynov");
            p = pb.start();
            final BufferedReader r = new BufferedReader(
                    new InputStreamReader(p.getErrorStream()));
            String line = r.readLine();
            while (line != null) {
                System.err.println(line);
                line = r.readLine();
            }
            r.close();
            p.waitFor();
            System.out.println(p.exitValue());

        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void importData(String path){
        Runtime rt = Runtime.getRuntime();
        Process p;
        ProcessBuilder pb;
        rt = Runtime.getRuntime();
        pb = new ProcessBuilder(
                "C:\\Program Files\\PostgreSQL\\12\\bin\\pg_restore.exe",
                "--host", "localhost",
                "--port", "5432",
                "--username", "postgres",
                "--dbname","Basketball",
                "--no-password",
                "--clean",
                "--verbose",path);
        try {
            final Map<String, String> env = pb.environment();
            env.put("PGPASSWORD", "martynov");
            p = pb.start();
            final BufferedReader r = new BufferedReader(
                    new InputStreamReader(p.getErrorStream()));
            String line = r.readLine();
            while (line != null) {
                System.err.println(line);
                line = r.readLine();
            }
            r.close();
            p.waitFor();
            System.out.println(p.exitValue());

        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Team> getTeam() throws SQLException {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("select * from team");
        List<Team> t1 = new ArrayList<>();
        while (rs.next()) {
            String id = rs.getString("id");
            String name = rs.getString("name");
            String city = rs.getString("city");
            String countOfChampions = rs.getString("countOfChampions");
            String season_place = rs.getString("season_place");
            String play_off_place = rs.getString("play_off_place");
            Team t = new Team(Integer.parseInt(id), name, city, Integer.parseInt(countOfChampions),
                    Integer.parseInt(season_place), Integer.parseInt(play_off_place));
            t1.add(t);
        }
        return t1;
    }

    public void insertTeam(Team team) {
        String call = "call  teamInsert(?,?,?,?,?,?)";
        try (CallableStatement stmt = connection.prepareCall(call)) {
            stmt.setInt(1, 10);
            stmt.setString(2, team.getName());
            stmt.setString(3, team.getCity());
            stmt.setInt(4, team.getCountOfChampions());
            stmt.setInt(5, team.getSeason_place());
            stmt.setInt(6, team.getPlay_off_place());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTeam(int id) {
        String del = "delete from team where id=?";
        try (PreparedStatement stm = connection.prepareStatement(del)) {
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTeam(Team team) {
        String call = "call  teamUpdate(?,?,?,?,?,?)";
        try (CallableStatement stmt = connection.prepareCall(call)) {
            stmt.setInt(1, team.getId());
            stmt.setString(2, team.getName());
            stmt.setString(3, team.getCity());
            stmt.setInt(4, team.getCountOfChampions());
            stmt.setInt(5, team.getSeason_place());
            stmt.setInt(6, team.getPlay_off_place());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Team> additionalTeam() throws SQLException {
        Statement st = null;
        ResultSet rs = null;
        try {
            st = connection.createStatement();
            rs = st.executeQuery("select id,name from team");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Team> t1 = new ArrayList<>();
        while (rs.next()) {
            String id = rs.getString("id");
            String name = rs.getString("name");
            Team t = new Team(Integer.parseInt(id), name);
            t1.add(t);
        }
        return t1;

    }

    public List<Player> getPlayer() throws SQLException {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("select * from player");
        List<Player> t1 = new ArrayList<>();
        while (rs.next()) {
            String id = rs.getString("id");
            String name = rs.getString("name");
            String age = rs.getString("age");
            String nationality = rs.getString("nationality");
            String player_position = rs.getString("player_position");
            String height = rs.getString("height");
            String weight = rs.getString("weight");
            String team_id = rs.getString("team_id");
            Player player = new Player(Integer.parseInt(id), name, Integer.parseInt(team_id), Integer.parseInt(age),
                    Integer.parseInt(weight), player_position, nationality, height);
            t1.add(player);
        }
        return t1;
    }

    public void insertPlayer(Player player) {
        String call = "call  playerInsert(?,?,?,?,?,?,?)";
        try (CallableStatement stmt = connection.prepareCall(call)) {
            stmt.setInt(1, player.getTeam_id());
            stmt.setInt(2, player.getAge());
            stmt.setString(3, player.getNationality());
            stmt.setString(4, player.getPlayer_position());
            stmt.setString(5, player.getHeight());
            stmt.setInt(6, player.getWeight());
            stmt.setString(7, player.getName());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePlayer(Player player) {
        String call = "call playerUpdate(?,?,?,?,?,?,?,?)";
        try (CallableStatement stmt = connection.prepareCall(call)) {
            stmt.setInt(1, player.getId());
            stmt.setInt(2, player.getAge());
            stmt.setString(3, player.getNationality());
            stmt.setString(4, player.getPlayer_position());
            stmt.setString(5, player.getHeight());
            stmt.setInt(6, player.getWeight());
            stmt.setString(7, player.getName());
            stmt.setInt(8, player.getTeam_id());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePlayer(Player player) {
        String del = "delete from player where id=?";
        try (PreparedStatement stm = connection.prepareStatement(del)) {
            stm.setInt(1, player.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Player> additionalPlayer() throws SQLException {
        Statement st = null;
        ResultSet rs = null;
        try {
            st = connection.createStatement();
            rs = st.executeQuery("select id,name from player");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Player> t1 = new ArrayList<>();
        while (rs.next()) {
            String id = rs.getString("id");
            String name = rs.getString("name");
            Player t = new Player(Integer.parseInt(id), name);
            t1.add(t);
        }
        return t1;
    }

    public List<Coach> getCoach() throws SQLException {
        List<Coach> t1 = new ArrayList();
        Statement statement = null;
        ResultSet rs = null;
        statement = connection.createStatement();
        rs = statement.executeQuery("select * from coach");
        while (rs.next()) {
            String id = rs.getString("id");
            String name = rs.getString("name");
            String speciality = rs.getString("speciality");
            String team_id = rs.getString("team_id");
            Coach coach = new Coach(Integer.parseInt(id), name, speciality, Integer.parseInt(team_id));
            t1.add(coach);
        }
        return t1;
    }

    public void insertCoach(Coach coach) {
        String call = "call insertCoach(?,?,?)";
        try (CallableStatement statement = connection.prepareCall(call)) {
            statement.setInt(1, coach.getTeam_id());
            statement.setString(2, coach.getName());
            statement.setString(3, coach.getSpeciality());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCoach(Coach coach) {
        String call = "call coachUpdate(?,?,?,?)";
        try (CallableStatement statement = connection.prepareCall(call)) {
            statement.setInt(1, coach.getId());
            statement.setString(2, coach.getName());
            statement.setString(3, coach.getSpeciality());
            statement.setInt(4, coach.getTeam_id());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCoach(Coach coach) {
        String call = "delete from coach where id=?";
        try (PreparedStatement statement = connection.prepareStatement(call)) {
            statement.setInt(1, coach.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Manager> getManager() throws SQLException {
        List<Manager> t1 = new ArrayList();
        Statement statement = null;
        ResultSet rs = null;
        statement = connection.createStatement();
        rs = statement.executeQuery("select * from manager");
        while (rs.next()) {
            String id = rs.getString("id");
            String name = rs.getString("name");
            String team_id = rs.getString("team_id");
            Manager manager = new Manager(Integer.parseInt(id), name, Integer.parseInt(team_id));
            t1.add(manager);
        }
        return t1;
    }

    public void insertManager(Manager manager) {
        String call = "call insertManager(?,?)";
        try (CallableStatement statement = connection.prepareCall(call)) {
            statement.setInt(1, manager.getTeam_id());
            statement.setString(2, manager.getName());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateManager(Manager manager) {
        String call = "call updateManger(?,?,?)";
        try (CallableStatement statement = connection.prepareCall(call)) {
            statement.setInt(1, manager.getId());
            statement.setString(2, manager.getName());
            statement.setInt(3, manager.getTeam_id());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteManager(Manager manager) {
        String call = "delete from manager where id=?";
        try (PreparedStatement statement = connection.prepareStatement(call)) {
            statement.setInt(1, manager.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ContractSponsor> getContractTeam() throws SQLException {
        List<ContractSponsor> t1 = new ArrayList();
        Statement statement = null;
        ResultSet rs = null;
        statement = connection.createStatement();
        rs = statement.executeQuery("select * from contract_sponsor_team");
        while (rs.next()) {
            String name = rs.getString("name");
            String team_id = rs.getString("team_id");
            String contruct_sum = rs.getString("contract_sum");
            ContractSponsor contractTeam = new ContractSponsor(name, Integer.parseInt(team_id), Integer.parseInt(contruct_sum));
            t1.add(contractTeam);
        }
        return t1;
    }

    public List<ContractSponsor> getContractPlayer() throws SQLException {
        List<ContractSponsor> t1 = new ArrayList();
        Statement statement = null;
        ResultSet rs = null;
        statement = connection.createStatement();
        rs = statement.executeQuery("select * from contract_sponsor_player");
        while (rs.next()) {
            String name = rs.getString("name");
            String team_id = rs.getString("player_id");
            String contract_sum = rs.getString("contract_sum");
            ContractSponsor contractTeam = new ContractSponsor(name, Integer.parseInt(team_id), Integer.parseInt(contract_sum));
            t1.add(contractTeam);
        }
        return t1;
    }

    public void addContractPlayer(ContractSponsor player) {
        String call = "call sponsorplayerinsert(?,?,?)";
        try (CallableStatement statement = connection.prepareCall(call)) {
            statement.setInt(1, player.getReference_id());
            statement.setString(2, player.getName());
            statement.setInt(3, player.getSumm());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateContractPlayer(ContractSponsor player) {
        String call = "call updateSponsorPlayer(?,?,?,?)";
        try (CallableStatement statement = connection.prepareCall(call)) {
            statement.setInt(1, player.getReference_id());
            statement.setString(2, player.getName());
            statement.setInt(3, player.getSumm());
            statement.setString(4, player.getOldName());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteContractPlayer(ContractSponsor player) {
        String call = "delete from contract_sponsor_player where name=?";
        try (PreparedStatement statement = connection.prepareStatement(call)) {
            statement.setString(1, player.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addContractTeam(ContractSponsor team) {
        String call = "call sponsorteaminsert(?,?,?)";
        try (CallableStatement statement = connection.prepareCall(call)) {
            statement.setInt(1, team.getReference_id());
            statement.setString(2, team.getName());
            statement.setInt(3, team.getSumm());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateContractTeam(ContractSponsor team) {
        String call = "call updateSponsorTeam(?,?,?,?)";
        try (CallableStatement statement = connection.prepareCall(call)) {
            statement.setInt(1, team.getReference_id());
            statement.setString(2, team.getName());
            statement.setInt(3, team.getSumm());
            statement.setString(4, team.getOldName());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteContractTeam(ContractSponsor team) {
        String call = "delete from contract_sponsor_team where name=?";
        try (PreparedStatement statement = connection.prepareStatement(call)) {
            statement.setString(1, team.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Progress> getProgress() throws SQLException {
        List<Progress> t1 = new ArrayList<>();
        String call = "select * from progress";
        Statement statement = connection.createStatement();
        ResultSet rs = null;
        rs = statement.executeQuery(call);
        while (rs.next()) {
            String id = rs.getString("id");
            String name = rs.getString("name");
            String unit = rs.getString("Unit");
            Progress progress = new Progress(Integer.parseInt(id), name, unit);
            t1.add(progress);
        }
        return t1;
    }

    public void insertProgress(Progress progress) {
        String call = "call progressInsert(?,?)";
        try (CallableStatement statement = connection.prepareCall(call)) {
            statement.setString(1, progress.getName());
            statement.setString(2, progress.getUnit());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProgress(Progress progress) {
        String call = "call updateProgress(?,?,?)";
        try (CallableStatement statement = connection.prepareCall(call)) {
            statement.setInt(1, progress.getId());
            statement.setString(2, progress.getName());
            statement.setString(3, progress.getUnit());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProgress(Progress progress) {
        String call = "delete from" +
                " progress where id=?";
        try (PreparedStatement statement = connection.prepareStatement(call)) {
            statement.setInt(1, progress.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PlayerStats> getPlayerStats() throws SQLException {
        List<PlayerStats> t1 = new ArrayList<>();
        String call = "select * from player_stats";
        Statement statement = connection.createStatement();
        ResultSet rs = null;
        rs = statement.executeQuery(call);
        while (rs.next()) {
            String player_id = rs.getString("player_id");
            String name = rs.getString("value");
            String progress_id = rs.getString("progress_id");
            PlayerStats playerStats = new PlayerStats(Integer.parseInt(player_id), name, Integer.parseInt(progress_id));
            t1.add(playerStats);
        }
        return t1;
    }

    public void insertPlayerStats(PlayerStats playerStats) {
        String call = " call playerstatsInsert(?,?,?)";
        try (CallableStatement statement = connection.prepareCall(call)) {
            statement.setInt(1, playerStats.getPlayer_id());
            statement.setString(2, playerStats.getValue());
            statement.setInt(3, playerStats.getProgress_id());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePlayerStats(PlayerStats playerStats) {
        String call = "call playerStatsUpdate(?,?,?,?,?)";
        try (CallableStatement statement = connection.prepareCall(call)) {
            statement.setInt(1, playerStats.getPlayer_id());
            statement.setString(2, playerStats.getValue());
            statement.setInt(3, playerStats.getProgress_id());
            statement.setInt(4, playerStats.getOld_player_id());
            statement.setInt(5, playerStats.getOld_reference_id());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePlayerStats(PlayerStats playerStats) {
        String st = "delete from player_stats where (player_id=? and progress_id=?)";
        try (PreparedStatement statement = connection.prepareStatement(st)) {
            statement.setInt(1, playerStats.getPlayer_id());
            statement.setInt(2, playerStats.getProgress_id());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getBack() {
        String dell = "DELETE FROM story_help where (id) IN (SELECT max(id) from story_help)";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(dell);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void fullGetBack() {
        String dell = "SELECT max(id) from story_help";
        ResultSet set = null;
        int value = 0;
        try {
            Statement statement = connection.createStatement();
            set = statement.executeQuery(dell);
            while (set.next()) {
                value = Integer.parseInt(set.getString("max"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < value; i++) {
            getBack();
        }
    }

    public void clearTemp() {
        String drop = "DROP TABLE story_help";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(drop);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String create = "CREATE TABLE story_help(id serial not null, kto varchar, " +
                "kto_id integer, gde varchar, gde_id integer,chto varchar, cogda timestamptz, " +
                "addID int, name varchar,addID2 int,Constraint story_help_pk Primary Key(id))";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(create);
            statement.executeUpdate("CREATE TRIGGER get_back AFTER DELETE ON story_help FOR EACH ROW EXECUTE PROCEDURE undo()");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Player> showPlayers(Team team) throws SQLException {
        CallableStatement st = null;
        ResultSet rs = null;
        st = connection.prepareCall("select * from player where team_id=?");
        st.setInt(1, team.getId());
        rs = st.executeQuery();
        List<Player> t1 = new ArrayList<>();
        while (rs.next()) {
            String id = rs.getString("id");
            String name = rs.getString("name");
            String age = rs.getString("age");
            String nationality = rs.getString("nationality");
            String player_position = rs.getString("player_position");
            String height = rs.getString("height");
            String weight = rs.getString("weight");
            String team_id = rs.getString("team_id");
            Player player = new Player(Integer.parseInt(id), name, Integer.parseInt(team_id), Integer.parseInt(age),
                    Integer.parseInt(weight), player_position, nationality, height);
            t1.add(player);
        }
        return t1;
    }
    public List<PlayerStats> showPlayerStats(Player player) {
        List<PlayerStats> playerStats = new ArrayList<>();
        String call = "SELECT player.Name,player_stats.value, progress.Name as Progress_Name FROM player_stats \n" +
                "INNER JOIN player on player.id=player_stats.player_id\n" +
                "INNER JOIN progress on progress.id=player_stats.progress_id where player_id=?";
        try (CallableStatement preparedStatement = connection.prepareCall(call)) {
            preparedStatement.setInt(1, player.getId());
            ResultSet set = preparedStatement.executeQuery();
            while (set.next()) {
                String name = set.getString("name");
                String value = set.getString("value");
                String progress = set.getString("progress_name");
                PlayerStats playerStats1 = new PlayerStats(name, value, progress);
                playerStats.add(playerStats1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerStats;
    }
    public List<PlayerStats> showPlayerStats(){
        List<PlayerStats> playerStats = new ArrayList<>();
        String call = "SELECT player.Name,player_stats.value, progress.Name as Progress_Name FROM player_stats \n" +
                "INNER JOIN player on player.id=player_stats.player_id " +
                "INNER JOIN progress on progress.id=player_stats.progress_id";
        try (CallableStatement preparedStatement = connection.prepareCall(call)) {
            ResultSet set = preparedStatement.executeQuery();
            while (set.next()) {
                String name = set.getString("name");
                String value = set.getString("value");
                String progress = set.getString("progress_name");
                PlayerStats playerStats1 = new PlayerStats(name, value, progress);
                playerStats.add(playerStats1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerStats;
    }
    public List<Coach> showCoaches(Team team) throws SQLException{
        CallableStatement st = null;
        ResultSet rs = null;
        st = connection.prepareCall("select * from coach where team_id=?");
        st.setInt(1, team.getId());
        rs = st.executeQuery();
        List<Coach> t1 = new ArrayList<>();
        while (rs.next()) {
            String id=rs.getString("id");
            String name=rs.getString("name");
            String speciality=rs.getString("speciality");
            String team_id=rs.getString("team_id");
            t1.add(new Coach(Integer.parseInt(id),name,speciality,Integer.parseInt(team_id)));
        }
        return t1;
    }
    public List<Manager> showManagers(Team team) throws SQLException{
        CallableStatement st = null;
        ResultSet rs = null;
        st = connection.prepareCall("select * from manager where team_id=?");
        st.setInt(1, team.getId());
        rs = st.executeQuery();
        List<Manager> t1 = new ArrayList<>();
        while (rs.next()) {
            String id=rs.getString("id");
            String name=rs.getString("name");
            String team_id=rs.getString("team_id");
            t1.add(new Manager(Integer.parseInt(id),name,Integer.parseInt(team_id)));
        }
        return t1;
    }
    public List<Player> showPlayers() throws SQLException {
        CallableStatement st = null;
        ResultSet rs = null;
        st = connection.prepareCall("select player.id,player.name,player.age,player.player_position,player.nationality,player.weight,player.height" +
                ",team.name as Team  from player\n" +
                "\t\t\tINNER JOIN team on team.id=player.team_id ;");
        rs = st.executeQuery();
        List<Player> t1 = new ArrayList<>();
        while (rs.next()) {
            String id = rs.getString("id");
            String name = rs.getString("name");
            String age = rs.getString("age");
            String nationality = rs.getString("nationality");
            String player_position = rs.getString("player_position");
            String height = rs.getString("height");
            String weight = rs.getString("weight");
            String team_name = rs.getString("Team");
            Player player = new Player(Integer.parseInt(id), name, team_name, Integer.parseInt(age),
                    Integer.parseInt(weight), player_position, nationality, height);
            t1.add(player);
        }
        return t1;
    }
    public List<Coach> showCoaches() throws SQLException{
        CallableStatement st = null;
        ResultSet rs = null;
        st = connection.prepareCall("select coach.id,coach.name,coach.speciality,team.name as Team  from coach\n" +
                "\t\t\tINNER JOIN team on team.id=coach.team_id ;");;
        rs = st.executeQuery();
        List<Coach> t1 = new ArrayList<>();
        while (rs.next()) {
            String id=rs.getString("id");
            String name=rs.getString("name");
            String speciality=rs.getString("speciality");
            String team_name=rs.getString("Team");
            t1.add(new Coach(Integer.parseInt(id),name,speciality,team_name));
        }
        return t1;
    }
    public List<Manager> showMangers() throws SQLException{
        CallableStatement st = null;
        ResultSet rs = null;
        st = connection.prepareCall("select manager.id,manager.name,team.name as Team  from manager\n" +
                "\t\t\tINNER JOIN team on team.id=manager.team_id ;");
        rs = st.executeQuery();
        List<Manager> t1 = new ArrayList<>();
        while (rs.next()) {
            String id=rs.getString("id");
            String name=rs.getString("name");
            String team_name=rs.getString("Team");
            t1.add(new Manager(Integer.parseInt(id),name,team_name));
        }
        return t1;
    }
    public   Map<String,List<Table>> searchByName(String names) throws  SQLException{
        CallableStatement st = null;
        ResultSet rs = null;
        Map<String,List<Table>> map=new HashMap<>();
        String call="select player.id,player.name,player.age,player.player_position," +
                "player.nationality,player.weight,player.height,team.name as Team  from player" +
                " INNER JOIN team on team.id=player.team_id where player.name=?";
        String call1="select coach.id,coach.name,coach.speciality,team.name as Team  from coach " +
                "INNER JOIN team on team.id=coach.team_id where coach.name=?";
        String call2="select manager.id,manager.name,team.name as Team  from manager " +
                "INNER JOIN team on team.id=manager.team_id  where manager.name=?";
        st=connection.prepareCall(call);
        st.setString(1,names);
        rs=st.executeQuery();
        List<Table> t1 = new ArrayList<>();
        while (rs.next()) {
            String id = rs.getString("id");
            String name = rs.getString("name");
            String age = rs.getString("age");
            String nationality = rs.getString("nationality");
            String player_position = rs.getString("player_position");
            String height = rs.getString("height");
            String weight = rs.getString("weight");
            String team_name = rs.getString("Team");
            Player player = new Player(Integer.parseInt(id), name, team_name, Integer.parseInt(age),
                        Integer.parseInt(weight), player_position, nationality, height);
            t1.add(player);
        }
        map.putIfAbsent("Player",t1);
        st=connection.prepareCall(call1);
        st.setString(1,names);
        rs=st.executeQuery();
        List<Table> t2=new ArrayList<>();
        while (rs.next()) {
            String id=rs.getString("id");
            String name=rs.getString("name");
            String speciality=rs.getString("speciality");
            String team_name=rs.getString("Team");
            t2.add(new Coach(Integer.parseInt(id),name,speciality,team_name));
        }
        map.putIfAbsent("Coach",t2);

        st=connection.prepareCall(call2);
        st.setString(1,names);
        rs=st.executeQuery();
        List<Table> t3=new ArrayList<>();
        while (rs.next()) {
            String id=rs.getString("id");
            String name=rs.getString("name");
            String team_name=rs.getString("Team");
            t3.add(new Manager(Integer.parseInt(id),name,team_name));
        }
        map.putIfAbsent("Manager",t3);

        return  map;
    }
    public List<ContractSponsor> showSponsorTeam(Team team) throws SQLException {
        String call="SELECT contract_sponsor_team.name, contract_sponsor_team.contract_sum, team.Name as team from contract_sponsor_team\n" +
                "INNER JOIN team on team.id=contract_sponsor_team.team_id\n" +
                "WHERE contract_sponsor_team.team_id=?;";
        CallableStatement statement=connection.prepareCall(call);
        statement.setInt(1,team.getId());
        ResultSet set=statement.executeQuery();
        List<ContractSponsor> list=new ArrayList<>();
        while(set.next()){
            String contract_sum=set.getString("contract_sum");
            String name=set.getString("name");
            String team_name=set.getString("team");
            ContractSponsor contractSponsor=new ContractSponsor(name,Integer.parseInt(contract_sum),team_name);
            list.add(contractSponsor);
        }
        return  list;
    }
    public List<ContractSponsor> showSponsorTeam() throws SQLException{
        String call="SELECT contract_sponsor_team.name, contract_sponsor_team.contract_sum, team.Name as team from contract_sponsor_team\n" +
                "INNER JOIN team on team.id=contract_sponsor_team.team_id";
        CallableStatement statement=connection.prepareCall(call);
        ResultSet set=statement.executeQuery();
        List<ContractSponsor> list=new ArrayList<>();
        while(set.next()){
            String contract_sum=set.getString("contract_sum");
            String name=set.getString("name");
            String team_name=set.getString("team");
            ContractSponsor contractSponsor=new ContractSponsor(name,Integer.parseInt(contract_sum),team_name);
            list.add(contractSponsor);
        }
        return  list;
    }
    public List<ContractSponsor> showSponsorPlayer(Player player) throws SQLException{
        String call="SELECT contract_sponsor_player.name, contract_sponsor_player.contract_sum, player.Name as Player from contract_sponsor_player\n" +
                "INNER JOIN player on player.id=contract_sponsor_player.player_id\n" +
                "WHERE contract_sponsor_player.player_id=?;";
        CallableStatement statement=connection.prepareCall(call);
        statement.setInt(1,player.getId());
        ResultSet set=statement.executeQuery();
        List<ContractSponsor> list=new ArrayList<>();
        while(set.next()){
            String contract_sum=set.getString("contract_sum");
            String name=set.getString("name");
            String player_name=set.getString("Player");
            ContractSponsor contractSponsor=new ContractSponsor(name,Integer.parseInt(contract_sum),player_name);
            list.add(contractSponsor);
        }
        return  list;
    }
    public List<ContractSponsor> showSponsorPlayer() throws SQLException{
        String call="SELECT contract_sponsor_player.name, contract_sponsor_player.contract_sum, player.Name as Player from contract_sponsor_player\n" +
                "INNER JOIN player on player.id=contract_sponsor_player.player_id";
        CallableStatement statement=connection.prepareCall(call);
        ResultSet set=statement.executeQuery();
        List<ContractSponsor> list=new ArrayList<>();
        while(set.next()){
            String contract_sum=set.getString("contract_sum");
            String name=set.getString("name");
            String player_name=set.getString("Player");
            ContractSponsor contractSponsor=new ContractSponsor(name,Integer.parseInt(contract_sum),player_name);
            list.add(contractSponsor);
        }
        return  list;
    }
    public List<ContractSponsor> showSponsorTeam(String name) throws SQLException {
        String call="SELECT contract_sponsor_team.name, contract_sponsor_team.contract_sum, team.Name as Team from contract_sponsor_team\n" +
                "INNER JOIN team on team.id=contract_sponsor_team.team_id\n" +
                "where contract_sponsor_team.name=?";
        CallableStatement statement=connection.prepareCall(call);
        statement.setString(1,name);
        ResultSet set=statement.executeQuery();
        List<ContractSponsor> list=new ArrayList<>();
        while (set.next())
        {
            String contract_sum=set.getString("contract_sum");
            String names=set.getString("name");
            String team_name=set.getString("team");
            ContractSponsor contractSponsor=new ContractSponsor(name,Integer.parseInt(contract_sum),team_name);
            list.add(contractSponsor);
        }
        return  list;
    }
    public   Map<String,List<Table>> searchByNameSponsor(String names) throws  SQLException{
        CallableStatement st = null;
        ResultSet rs = null;
        Map<String,List<Table>> map=new HashMap<>();
        String call="select player.id,player.name,player.age,player.player_position," +
                "player.nationality,player.weight,player.height,team.name as Team  from player" +
                " INNER JOIN team on team.id=player.team_id where player.name=?";
        String call1="SELECT contract_sponsor_player.name, contract_sponsor_player.contract_sum, player.Name as Player from contract_sponsor_player " +
                "INNER JOIN player on player.id=contract_sponsor_player.player_id where contract_sponsor_player.name=?";

        st=connection.prepareCall(call);
        st.setString(1,names);
        rs=st.executeQuery();
        List<Table> t1 = new ArrayList<>();
        while (rs.next()) {
            String id = rs.getString("id");
            String name = rs.getString("name");
            String age = rs.getString("age");
            String nationality = rs.getString("nationality");
            String player_position = rs.getString("player_position");
            String height = rs.getString("height");
            String weight = rs.getString("weight");
            String team_name = rs.getString("Team");
            Player player = new Player(Integer.parseInt(id), name, team_name, Integer.parseInt(age),
                    Integer.parseInt(weight), player_position, nationality, height);
            t1.add(player);
        }
        map.putIfAbsent("Player",t1);
        st=connection.prepareCall(call1);
        st.setString(1,names);
        rs=st.executeQuery();
        List<Table> t2=new ArrayList<>();
        while (rs.next()) {
            String contract_sum=rs.getString("contract_sum");
            String name=rs.getString("name");
            String player_name=rs.getString("Player");
            ContractSponsor contractSponsor=new ContractSponsor(name,Integer.parseInt(contract_sum),player_name);
            t2.add(contractSponsor);
        }
        map.putIfAbsent("Contract Player",t2);
        return  map;
    }
}


