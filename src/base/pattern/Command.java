package base.pattern;


public class Command {

    public interface Action {
        void execute();
    }

    /*Receiver class*/
    public class Light {
        public Light() {
        }

        public void turnOn() {
            System.out.println("The light is on");
        }

        public void turnOff() {
            System.out.println("The light is off");
        }
    }

    /*the Command for turning on the light*/
    public class TurnOnLightCommand implements Action {
        private Light theLight;

        public TurnOnLightCommand(Light light) {
            this.theLight = light;
        }

        public void execute() {
            theLight.turnOn();
        }
    }

    /*the Command for turning off the light*/

    public class TurnOffLightCommand implements Action {
        private Light theLight;

        public TurnOffLightCommand(Light light) {
            this.theLight = light;
        }

        public void execute() {
            theLight.turnOff();
        }
    }

    /*invoker*/
    public class Switch {
        private Action flipUpCommand;
        private Action flipDownCommand;

        public Switch(Action flipUpCommand, Action flipDownCommand) {
            this.flipUpCommand = flipUpCommand;
            this.flipDownCommand = flipDownCommand;
        }

        public void flipUp() {
            flipUpCommand.execute();
        }

        public void flipDown() {
            flipDownCommand.execute();
        }
    }

    public class TestCommand {
        public void main(String[] args) {
            // создаем объект, который будет использоваться
            Light light = new Light();
            // создаем объекты для всех умений объекта Light:
            Action switchUp = new TurnOnLightCommand(light);
            Action switchDown = new TurnOffLightCommand(light);

            // Создаемтся invoker, с которым мы будем непосредственно контактировать:
            Switch s = new Switch(switchUp, switchDown);

            // вот проверка этого, используем методы:
            s.flipUp();
            s.flipDown();
        }
    }
}
