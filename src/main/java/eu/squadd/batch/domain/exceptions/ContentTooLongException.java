/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.domain.exceptions;

/**
 *
 * @author smorcja
 */
public class ContentTooLongException extends Exception {

    public ContentTooLongException(String message) {
        super(message);
    }
}
