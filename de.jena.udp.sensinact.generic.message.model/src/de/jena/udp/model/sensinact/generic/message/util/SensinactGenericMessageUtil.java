/**
 * Copyright (c) 2012 - 2023 Data In Motion and others.
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package de.jena.udp.model.sensinact.generic.message.util;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EStructuralFeature;

import de.jena.udp.model.sensinact.generic.message.MessageFactory;
import de.jena.udp.model.sensinact.generic.message.MessagePackage;
import de.jena.udp.model.sensinact.generic.message.UpdateMessage;

/**
 * 
 * @author ilenia
 * @since May 11, 2023
 */
public class SensinactGenericMessageUtil {
	
	/**
	 * @param type the type to check for
	 * @return A suitable {@link UpdateMessage}
	 * @throws IllegalArgumentException if no {@link UpdateMessage} has a fitting type 
	 */
	public static UpdateMessage createUpdateMessageForType(final Class<?> type) {

		EList<EClassifier> eClassifiers = MessagePackage.eINSTANCE.getEClassifiers();
		return eClassifiers.stream().filter(EClass.class::isInstance).map(EClass.class::cast)
				.filter(ec -> isForType(ec, type)).findFirst().map(MessageFactory.eINSTANCE::create)
				.map(UpdateMessage.class::cast)
				.orElseThrow(() -> new IllegalArgumentException("No UpdateMessage found for Type " + type));

	}

	private static boolean isForType(final EClass ec, final Class<?> type) {
		EStructuralFeature feature = ec.getEStructuralFeature("oldValue");
		if (feature == null) {
			return false;
		}
		return feature.getEType().getInstanceClass() != null && 
				(feature.getEType().getInstanceClass().equals(type) || 
						box(feature.getEType().getInstanceClass()).equals(type) ||
						feature.getEType().getInstanceClass().equals(box(type)));
	}
	
	private static Class<?> box(Class<?> clazz){
		
		if(clazz.equals(int.class)) {
			return Integer.class;
		} else if(clazz.equals(double.class)) {
			return Double.class;
		} else if(clazz.equals(long.class)) {
			return Long.class;
		} else if(clazz.equals(boolean.class)) {
			return Boolean.class;
		} else if(clazz.equals(byte.class)) {
			return Byte.class;
		} else if(clazz.equals(char.class)) {
			return Character.class;
		} else if(clazz.equals(float.class)) {
			return Float.class;
		} else if(clazz.equals(short.class)) {
			return Short.class;
		}
		
		return clazz;
	}
	


}
