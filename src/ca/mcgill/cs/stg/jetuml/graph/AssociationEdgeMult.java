/*******************************************************************************
 * JetUML - A desktop application for fast UML diagramming.
 *
 * Copyright (C) 2015-2017 by the contributors of the JetUML project.
 *
 * See: https://github.com/prmr/JetUML
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/

/**
 * @author Martin P. Robillard
 */

package ca.mcgill.cs.stg.jetuml.graph;

import ca.mcgill.cs.stg.jetuml.framework.ArrowHead;
import ca.mcgill.cs.stg.jetuml.framework.MultiLineString;
import ca.mcgill.cs.stg.jetuml.framework.SegmentationStyle;
import ca.mcgill.cs.stg.jetuml.framework.SegmentationStyleFactory;

/**
 *  An edge that that represents a UML association, with optional 
 *  labels and directionality.
 */
public class AssociationEdgeMult extends ClassRelationshipEdge
{
	/**
	 * Possible directionalities for an association.
	 */
	public enum Directionality 
	{None, Start, End, Both}
	public enum Mult {
		NONE(""),
	    ONE("1"),
	    ZERO_ONE("0..1"),
	    ONEMANY("1..n"),
	    ZEROMANY("0..n"),
	    ;

	    private final String text;

	    /**
	     * @param text
	     */
	    private Mult(final String text) {
	        this.text = text;
	    }

	    /* (non-Javadoc)
	     * @see java.lang.Enum#toString()
	     */
	    @Override
	    public String toString() {
	        return text;
	    }
	}
	private Node startNode;
	private Node endNode;
	
	
	
	//private MultiLineString aAttributes;
	private Directionality aDirectionality = Directionality.None;
	private Mult aStartMult = Mult.NONE;
	private Mult aEndMult = Mult.NONE;
	
	/**
	 * Creates an association edge with no labels.
	 * and no directionality
	 */
	public AssociationEdgeMult()
	{
		this.startNode = null;
		this.endNode = null;
	}
		
	public void setStartMult( Mult pStartMult){
		aStartMult = pStartMult;
		if(pStartMult != Mult.ZERO_ONE && pStartMult != Mult.ZEROMANY && pStartMult != Mult.NONE){
			this.addAttribute(endNode,startNode);
		}
		else{
			removeAttribute(endNode,startNode);
		}
		this.setStartLabel(aStartMult.text);
		
	}
	public Mult getStartMult(){
		return aStartMult;
	}
	
	public void setEndMult( Mult pEndMult){
		aEndMult = pEndMult;
		if(pEndMult != Mult.ZERO_ONE && pEndMult != Mult.ZEROMANY && pEndMult != Mult.NONE){
			this.addAttribute(startNode,endNode);
		}
		else{
			removeAttribute(startNode,endNode);
		}
		this.setEndLabel(aEndMult.text);
	}
	public Mult getEndMult(){
		return aEndMult;
	}
	
	private void addAttribute(Node target, Node reference){
		ClassNode targetClass = (ClassNode) target;
		ClassNode referenceClass = (ClassNode) reference;
		if(!targetClass.getAttributes().getText().contains((CharSequence) referenceClass.getName().getText())){
			MultiLineString newVal = new MultiLineString();
			newVal.setText(targetClass.getAttributes().getText() + "\n" + referenceClass.getName() + " var" + referenceClass.getName());
			targetClass.setAttributes(newVal);
		}
	}
	
	private void removeAttribute(Node target, Node reference){
		ClassNode targetClass = (ClassNode) target;
		ClassNode referenceClass = (ClassNode) reference;
		if(targetClass.getAttributes().getText().contains((CharSequence) referenceClass.getName().getText())){
			MultiLineString newVal = new MultiLineString();
			newVal.setText(targetClass.getAttributes().getText().replaceAll(
					referenceClass.getName().getText() + " var" + referenceClass.getName().getText(), ""));
			targetClass.setAttributes(newVal);
		}
	}
	
	/**
	 * @param pDirectionality The desired directionality.
	 */
	public void setDirectionality( Directionality pDirectionality )
	{
		aDirectionality = pDirectionality;
	}
	
	/**
	 * @return The directionality of this association.
	 */
	public Directionality getDirectionality()
	{
		return aDirectionality;
	}
	
	@Override
	protected ArrowHead obtainStartArrowHead()
	{
		if( aDirectionality == Directionality.Both || aDirectionality == Directionality.Start )
		{
			return ArrowHead.V;
		}
		else
		{
			return ArrowHead.NONE;
		}
	}
	
	@Override
	protected ArrowHead obtainEndArrowHead()
	{
		if( aDirectionality == Directionality.Both || aDirectionality == Directionality.End )
		{
			return ArrowHead.V;
		}
		else
		{
			return ArrowHead.NONE;
		}
	}
	
	@Override
	public SegmentationStyle obtainSegmentationStyle()
	{
		return SegmentationStyleFactory.createHVHStrategy();
	}
	
	@Override
	public void connect(Node pStart, Node pEnd, Graph pGraph)
	{
		super.connect(pStart, pEnd, pGraph);
		assert pStart instanceof ClassNode;
		assert pEnd instanceof ClassNode;
		startNode = pStart;
		endNode = pEnd;
	}
}

