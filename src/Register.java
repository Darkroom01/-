import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Register {
    private List<Member> members;

    public Register() {
        this.members = new ArrayList<>();
        loadMembers();
    }

    public void addMember(String username, String password, double balance) {
        Member member = new Member(username, password, balance);
        members.add(member);
        saveMembers();
    }

    public void loadMembers() {
        try (BufferedReader reader = new BufferedReader(new FileReader("members.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] memberData = line.split(",");
                String username = memberData[0];
                String password = memberData[1];
                double balance = Double.parseDouble(memberData[2]);
                Member member = new Member(username, password, balance);
                member.loadTransactions(); 
                members.add(member);
            }
        } catch (IOException e) {
        }
    }

    public void saveMembers() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("members.txt"))) {
            for (Member member : members) {
                writer.println(member.getUsername() + "," + member.getPassword() + "," + member.getBalance());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isMemberExists(String username) {
        for (Member member : members) {
            if (member.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public Member getMemberByUsername(String username) {
        for (Member member : members) {
            if (member.getUsername().equals(username)) {
                return member;
            }
        }
        return null;
    }

    public boolean isLoginValid(String username, String password) {
        for (Member member : members) {
            if (member.getUsername().equals(username) && member.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}