package Decorator;

public class InsuranceAddOnDecorator extends AddOnDecorator{

    public InsuranceAddOnDecorator(AddOn decoratedAddOn){
        super(decoratedAddOn);
    }

    @Override
    public void getAddOnPrice(){
        decoratedAddOn.getAddOnPrice();
        setAddOnPrice(decoratedAddOn);
    }

    private void setAddOnPrice(AddOn decoratedAddOn){
        System.out.println("Insurance added to Pet for $50");
    }
}
