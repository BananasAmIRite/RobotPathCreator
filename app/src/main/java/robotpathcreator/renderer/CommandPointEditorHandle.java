public class CommandPointEditorHandle extends PathPointEditorHandle {
    private final CommandPathPoint commandPoint; 
    public CommandPointEditorHandle(CommandPathPoint point) {
        super(point); 
        this.commandPoint = point; 
    }
}