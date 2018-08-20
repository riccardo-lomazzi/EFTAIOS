package eftaios.model.events;

import org.junit.Before;
import org.junit.Test;

import eftaios.view.EventVisitor;

public class GameEventTest {

    
    private EventVisitor visitor;
    private GameEvent event;

    @Before
    public void setUp() throws Exception {
         
        visitor = new EventVisitor() {
            
            @Override
            public void visitEvent(ConnectedToGameEvent connectedToGameEvent) {
            }
            
            @Override
            public void visitEvent(UnableToAttackEvent event) {
            }
            
            @Override
            public void visitEvent(TooMuchItemsEvent event) {
            }
            
            @Override
            public void visitEvent(SuccessfulUseOfSpotLightItemEvent event) {
            }
            
            @Override
            public void visitEvent(SuccessfulUseOfItemEvent event) {
            }
            
            @Override
            public void visitEvent(SuccessfulMoveOnWallSectorEvent event) {
            }
            
            @Override
            public void visitEvent(SuccessfulMoveOnStartingSectorEvent event) {
            }
            
            @Override
            public void visitEvent(SuccessfulMoveOnSafeSectorEvent event) {
            }
            
            @Override
            public void visitEvent(SuccessfulMoveOnEscapePodSectorEvent event) {
            }
            
            @Override
            public void visitEvent(SuccessfulMoveOnDangerousSectorEvent event) {
            }
            
            @Override
            public void visitEvent(SuccessfulItemAdditionEvent event) {
            }
            
            @Override
            public void visitEvent(SuccessfulGivenItemListEvent event) {
            }
            
            @Override
            public void visitEvent(SuccessfulAttackEvent event) {
            }
            
            @Override
            public void visitEvent(StartPlayerTurnEvent event) {
            }
            
            @Override
            public void visitEvent(SilenceEvent event) {
            }
            
            @Override
            public void visitEvent(RedEscapePodEvent event) {
            }
            
            @Override
            public void visitEvent(NoiseInYourSectorEvent event) {
            }
            
            @Override
            public void visitEvent(NoiseInAnySectorEvent event) {
            }
            
            @Override
            public void visitEvent(LogPrintEvent event) {
            }
            
            @Override
            public void visitEvent(ItemRequestEvent event) {
            }
            
            @Override
            public void visitEvent(IOErrorEvent event) {
            }
            
            @Override
            public void visitEvent(IllegalPlayerEvent event) {
            }
            
            @Override
            public void visitEvent(IllegalActionEvent event) {
            }
            
            @Override
            public void visitEvent(GreenEscapePodEvent event) {
            }
            
            @Override
            public void visitEvent(GameStartedEvent event) {
            }
            
            @Override
            public void visitEvent(EndGameEvent event) {
            }
            
            @Override
            public void visitEvent(EndOfTurnEvent event) {
            }
        };
    }

    @Test
    public void testAcceptVisit0() {
        event = new ConnectedToGameEvent("string test");
        event.acceptVisit(visitor);
        event = new GreenEscapePodEvent("string test");
        event.acceptVisit(visitor);
        event = new IllegalActionEvent("string test");
        event.acceptVisit(visitor);
        event = new IllegalPlayerEvent("string test");
        event.acceptVisit(visitor);
        event = new IOErrorEvent("string test");
        event.acceptVisit(visitor);
        event = new ItemRequestEvent("string test",null);
        event.acceptVisit(visitor);
        event = new LogPrintEvent("string test",null);
        event.acceptVisit(visitor);
        event = new NoiseInAnySectorEvent("string test");
        event.acceptVisit(visitor);
    }

    @Test
    public void testAcceptVisit1() {
        event = new EndGameEvent("string test");
        event.acceptVisit(visitor);
        event = new NoiseInYourSectorEvent("string test");
        event.acceptVisit(visitor);
        event = new RedEscapePodEvent("string test");
        event.acceptVisit(visitor);
        event = new SilenceEvent("string test");
        event.acceptVisit(visitor);
        event = new StartPlayerTurnEvent("string test");
        event.acceptVisit(visitor);
        event = new SuccessfulAttackEvent("string test",null);
        event.acceptVisit(visitor);
        event = new SuccessfulGivenItemListEvent("string test");
        event.acceptVisit(visitor);
        event = new SuccessfulItemAdditionEvent("string test");
        event.acceptVisit(visitor);
    }

    @Test
    public void testAcceptVisit2() {
        event = new EndOfTurnEvent("string test");
        event.acceptVisit(visitor);
        event = new SuccessfulMoveOnDangerousSectorEvent("string test");
        event.acceptVisit(visitor);
        event = new SuccessfulMoveOnEscapePodSectorEvent("string test");
        event.acceptVisit(visitor);
        event = new SuccessfulMoveOnSafeSectorEvent("string test");
        event.acceptVisit(visitor);
        event = new SuccessfulMoveOnStartingSectorEvent("string test");
        event.acceptVisit(visitor);
        event = new SuccessfulMoveOnWallSectorEvent("string test");
        event.acceptVisit(visitor);
        event = new SuccessfulUseOfItemEvent("string test");
        event.acceptVisit(visitor);
        event = new SuccessfulUseOfSpotLightItemEvent("string test",null);
        event.acceptVisit(visitor);
    }

    @Test
    public void testAcceptVisit3() {
        event = new GameStartedEvent("string test");
        event.acceptVisit(visitor);
        event = new TooMuchItemsEvent("string test",null,null);
        event.acceptVisit(visitor);
        event = new UnableToAttackEvent("string test");
        event.acceptVisit(visitor);
    }

}
