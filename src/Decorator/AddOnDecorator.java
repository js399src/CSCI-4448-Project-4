package Decorator;

public abstract class AddOnDecorator implements AddOn{

    protected AddOn decoratedAddOn;

    public AddOnDecorator(AddOn decoratedAddOn){
        this.decoratedAddOn = decoratedAddOn;
    }

    public void getAddOnPrice(){
        decoratedAddOn.getAddOnPrice();
    }
}
