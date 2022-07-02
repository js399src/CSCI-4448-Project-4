package Decorator;

public class VetCheckupAddOnDecorator extends AddOnDecorator{
    public VetCheckupAddOnDecorator(AddOn decoratedAddOn){
        super(decoratedAddOn);
    }

    @Override
    public void getAddOnPrice(){
        decoratedAddOn.getAddOnPrice();
        setAddOnPrice(decoratedAddOn);
    }

    private void setAddOnPrice(AddOn decoratedAddOn){
        System.out.println("Pre Paid Vet Checkup added to Pet for $25");
    }
}
