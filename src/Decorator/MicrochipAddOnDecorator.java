package Decorator;

public class MicrochipAddOnDecorator extends AddOnDecorator{

    public MicrochipAddOnDecorator(AddOn decoratedAddOn){
        super(decoratedAddOn);
    }

    @Override
    public void getAddOnPrice(){
        decoratedAddOn.getAddOnPrice();
        setAddOnPrice(decoratedAddOn);
    }

    private void setAddOnPrice(AddOn decoratedAddOn){
        System.out.println("Microchip added to Pet for $50");
    }
}
