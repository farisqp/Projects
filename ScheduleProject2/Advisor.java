public class Advisor extends Person implements IAdvisor {
    private ILocation office;

    public Advisor(int id, String name, String email, ISchedule schedule, ILocation office) {
        super(id, name, email, schedule);
        this.office = office;
    }

    public ILocation getOffice() { return office; }

    @Override
    public String toString() {
        return "Advisor{" + "id=" + getId() + ", name='" + getName() + '\'' + ", office=" + office.getName() + '}';
    }
}